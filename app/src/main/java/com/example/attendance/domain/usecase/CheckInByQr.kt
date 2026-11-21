package com.example.attendance.domain.usecase

import com.example.attendance.domain.AttendanceStatus
import com.example.attendance.domain.CheckInResult
import com.example.attendance.domain.SignInMethodType
import com.example.attendance.domain.model.AttendanceRecord
import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.repository.AttendanceRepository
import com.example.attendance.domain.repository.PersonRepository
import com.example.attendance.domain.repository.ScheduleRepository
import com.example.attendance.domain.util.TransactionRunner
import java.time.Clock
import java.util.UUID
import javax.inject.Inject

class CheckInByQr @Inject constructor(
    private val people: PersonRepository,
    private val schedules: ScheduleRepository,
    private val attendance: AttendanceRepository,
    private val transactions: TransactionRunner,
    private val clock: Clock
){
    suspend operator fun invoke(org: Organization, token: String): CheckInResult =
        transactions.inTransaction {
        val person = people.findByQrToken(org.id, token.trim())
            ?: return@inTransaction CheckInResult.Unknown

            val  now = clock.instant().atZone(org.zoneId)
            val today = now.toLocalDate()

            attendance.findForDay(person.id, today)?.let {
                return@inTransaction CheckInResult.AlreadyIn(person, it.timestamp)
            }

            val schedule = person.groupId?.let { schedules.getForGroup(it) }
            val  status  = if (schedule != null && now.toLocalTime().isAfter(schedule.lateAfter))
                AttendanceStatus.LATE else AttendanceStatus.ON_TIME

            attendance.record(
                AttendanceRecord(
                    id = UUID.randomUUID().toString(),
                    orgId = org.id,
                    personId = person.id,
                    timestamp = now.toInstant(),
                    date = today,
                    method = SignInMethodType.QR,
                    status = status
                )
            )

            CheckInResult.Success(person,status,now.toInstant())
    }
}