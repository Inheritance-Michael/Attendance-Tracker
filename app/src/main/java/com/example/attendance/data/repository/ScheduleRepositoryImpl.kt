package com.example.attendance.data.repository

import com.example.attendance.data.local.dao.ScheduleDao
import com.example.attendance.data.mapper.toDomain
import com.example.attendance.data.mapper.toEntity
import com.example.attendance.domain.model.Schedule
import com.example.attendance.domain.repository.ScheduleRepository
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val dao: ScheduleDao
) : ScheduleRepository {
    override suspend fun getForGroup(groupId: String): Schedule? {
        return dao.getForGroup(groupId)?.toDomain()
    }

    override suspend fun save(
        orgId: String,
        schedule: Schedule
    ) {
        dao.upsert(schedule.toEntity(orgId))
    }
}