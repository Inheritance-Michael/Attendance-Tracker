package com.example.attendance.domain.repository

import com.example.attendance.domain.model.AttendanceEntry
import com.example.attendance.domain.model.AttendanceRecord
import com.example.attendance.domain.model.Person
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate


interface AttendanceRepository {
    fun observePresentCount(orgId: String, date: LocalDate): Flow<Int>
    fun observeToday(orgId: String, date: LocalDate): Flow<List<AttendanceEntry>>
    fun observeAbsent(orgId: String, date: LocalDate): Flow<List<Person>>
    suspend fun findForDay(personId: String, date: LocalDate): AttendanceRecord?
    suspend fun record(record: AttendanceRecord)
}