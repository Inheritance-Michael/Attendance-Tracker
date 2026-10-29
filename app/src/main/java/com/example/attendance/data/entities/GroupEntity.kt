package com.example.attendance.data.entities

import android.service.notification.Condition.newId
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "groups", indices = [Index("orgId")])
data class GroupEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val orgId: String,
    val name: String,
    val scheduleId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
