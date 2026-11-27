package com.example.attendance.domain.usecase

import com.example.attendance.domain.model.NewOrganisation
import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.model.Schedule
import com.example.attendance.domain.model.util.CreateOrganizationResult
import com.example.attendance.domain.model.util.SetUpError
import com.example.attendance.domain.repository.AdminSecurityRepository
import com.example.attendance.domain.repository.OrganizationRepository
import com.example.attendance.domain.repository.ScheduleRepository
import com.example.attendance.domain.service.PinHasher
import com.example.attendance.domain.util.TransactionRunner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

class CreateOrganization @Inject constructor(
    private val organization: OrganizationRepository,
    private val schedule: ScheduleRepository,
    private val security: AdminSecurityRepository,
    private val hasher: PinHasher,
    private val transaction: TransactionRunner
) {
    suspend operator fun invoke(input: NewOrganisation): CreateOrganizationResult{
        val error = buildSet {
            if(input.name.isBlank()) add(SetUpError.NAME_REQUIRED)
            if(input.personLabel.isBlank()) add(SetUpError.PERSON_LABEL_REQUIRED)
            if(input.groupLabel.isBlank()) add(SetUpError.GROUP_LABEL_REQUIRED)
            if(runCatching { ZoneId.of(input.timeZone) }.isFailure) add(SetUpError.TIMEZONE_INVALID)
            if(!input.pin.matches(regex = Regex("\\d{6}"))) add(SetUpError.PIN_INVALID)
            else if(input.pin != input.confirmPin) add(SetUpError.PIN_MISMATCH)
        }
        if(error.isNotEmpty()) return CreateOrganizationResult.Invalid(error)

        val hash = withContext(Dispatchers.Default){hasher.hash(input.pin)}

        val org = Organization(
            id = UUID.randomUUID().toString(),
            name = input.name,
            personLabel = input.personLabel.trim(),
            groupLabel = input.groupLabel.trim(),
            zoneId = ZoneId.of(input.timeZone)
        )

        transaction.inTransaction {
            organization.create(org)
            security.setPin(orgId = org.id, hash= hash)
            schedule.save(
                orgId = org.id,
                schedule = Schedule(
                    id = UUID.randomUUID().toString(),
                    name = "Regular Day",
                    startTime = LocalTime.of(8,0),
                    endTime = LocalTime.of(15,0),
                    graceMinutes = 15,
                    days = setOf(
                        DayOfWeek.MONDAY,
                        DayOfWeek.TUESDAY,
                        DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY,
                        DayOfWeek.FRIDAY
                    )
                )
            )
        }
        return CreateOrganizationResult.Create(org)
    }
}