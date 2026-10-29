package com.example.attendance.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "organization")
data class OrganizationEntity(
    val name: String,
    val parsonLabel: String = "student",
    val groupLabel: String = "Class",
    val timezone: String = "Africa/Lagos",
    val adminPinHash: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)