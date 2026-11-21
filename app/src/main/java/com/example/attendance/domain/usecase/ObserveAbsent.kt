package com.example.attendance.domain.usecase

import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.model.Person
import com.example.attendance.domain.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class ObserveAbsent @Inject constructor(
    private val attendance: AttendanceRepository,
    private val clock: Clock
) {
    operator fun invoke(org: Organization): Flow<List<Person>>{
        return attendance.observeAbsent(org.id, date = LocalDate.now(clock.withZone(org.zoneId)) )
    }
}