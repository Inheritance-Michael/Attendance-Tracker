package com.example.attendance.data.repository

import com.example.attendance.data.local.dao.AttendanceDao
import com.example.attendance.data.mapper.toDomain
import com.example.attendance.data.mapper.toEntity
import com.example.attendance.domain.model.AttendanceEntry
import com.example.attendance.domain.model.AttendanceRecord
import com.example.attendance.domain.model.Person
import com.example.attendance.domain.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class AttendanceRepositoryImpi @Inject constructor(
    private val dao: AttendanceDao
) : AttendanceRepository {

    override fun observePresentCount(
        orgId: String,
        date: LocalDate
    ): Flow<Int> {
        return dao.observePresentCount(orgId, date.toString())
    }

    override fun observeToday(
        orgId: String,
        date: LocalDate
    ): Flow<List<AttendanceEntry>> {
        return dao.observeToday(orgId, date.toString()).map { rows -> rows.map { it.toDomain() } }
    }

    override fun observeAbsent(
        orgId: String,
        date: LocalDate
    ): Flow<List<Person>> {
       return dao.observeAbsent(orgId, date.toString()).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun findForDay(
        personId: String,
        date: LocalDate
    ): AttendanceRecord? {
        return dao.firstForDay(personId,date.toString())?.toDomain()
    }

    override suspend fun record(record: AttendanceRecord) {
       dao.insert(record.toEntity())
    }
}