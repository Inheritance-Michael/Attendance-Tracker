package com.example.attendance.domain.repository

import com.example.attendance.domain.model.Schedule

interface ScheduleRepository {
    suspend fun getForGroup(groupId: String): Schedule?
    suspend fun save(orgId: String, schedule: Schedule)
}