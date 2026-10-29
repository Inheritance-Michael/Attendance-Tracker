package com.example.attendance.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.attendance.data.entities.SchedulesEntity

@Dao
interface ScheduleDao{

    @Upsert
    suspend fun upsert(schedule: SchedulesEntity)

    @Query(value = "SELECT * FROM schedules WHERE id = :id")
    suspend fun getById(id: String): SchedulesEntity?
}
