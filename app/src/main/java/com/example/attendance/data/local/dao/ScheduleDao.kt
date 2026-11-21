package com.example.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.attendance.data.local.entities.SchedulesEntity

@Dao
interface ScheduleDao{

    @Upsert
    suspend fun upsert(schedule: SchedulesEntity)

    @Query(value = "SELECT * FROM schedules WHERE id = :id")
    suspend fun getById(id: String): SchedulesEntity?

    @Query("""
    SELECT s.* FROM schedules s
    JOIN `groups` g ON g.scheduleId = s.id
    WHERE g.id = :groupId
    LIMIT 1
""")
    suspend fun getForGroup(groupId: String): SchedulesEntity?
}
