package com.example.attendance.data.entities.relationship

import androidx.room.Entity
import androidx.room.Index
@Entity(
    tableName = "attendance_records",
    indices = [
        Index(*["orgId","localDate"]),
        Index(*["personId","localDate"]),
        Index("syncStatus")
    ]
)
data class AttendanceRecordEntity(
    val orgId: String,
    val personId: String,
    val deviceId: String? = null,
    val siteId: String? = null,
    val timestamp: Long,
    val localDate: String,
    val direction: String = "in",
    val method: String,
    val status: String,
    val syncStatus: Int = 0,
    val note: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
