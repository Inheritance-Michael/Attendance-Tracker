package com.example.attendance.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "person",
    indices = [
        Index("orgId"),
        Index("groupId")
    ]
)
data class PersonEntity(
    val fullName: String,
    val role: String = "member",
    val photoPath: String? = null,
    val guardianPhone: String? =  null,
    val customFieldsJson: String = "{}",
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val orgId: String,
    val groupId: String? = null,
    val externalId: String? = null,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)
