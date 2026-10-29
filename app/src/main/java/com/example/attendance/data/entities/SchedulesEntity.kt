package com.example.attendance.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "schedules",
    indices = [Index("orgId")]
)
data class SchedulesEntity(
    val orgId: String,
    val name: String,
    val startTime: String,
    val endTime: String,
    val graceMinutes: Int = 15,
    val daysOfWeek: String = "1,2,3,4,5",
    val createdAt: Long = System.currentTimeMillis(),
    val updateAt: Long = System.currentTimeMillis(),
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)