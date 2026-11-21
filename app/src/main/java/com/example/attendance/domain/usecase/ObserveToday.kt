package com.example.attendance.domain.usecase

import com.example.attendance.domain.model.AttendanceEntry
import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject


class ObserveToday @Inject constructor(
    private val attendance: AttendanceRepository,
    private val clock: Clock
) {
    operator fun invoke(org: Organization): Flow<List<AttendanceEntry>> =
        attendance.observeToday(org.id, LocalDate.now(clock.withZone(org.zoneId)))
}