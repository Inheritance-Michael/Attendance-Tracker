package com.example.attendance.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "credentials",
    indices = [
        Index("orgId"),
        Index("personId"),
        Index(*["type","value"], unique = true)
    ]
)
data class CredentialEntity(
    val type: String,
    val value: String,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updated: Long = System.currentTimeMillis(),
    val orgId: String,
    val personId: String,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)
