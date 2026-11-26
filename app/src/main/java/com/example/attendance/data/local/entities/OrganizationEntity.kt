package com.example.attendance.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "organization")
data class OrganizationEntity(
    val name: String,
    val personLabel: String = "student",
    val groupLabel: String = "Class",
    val timezone: String = "Africa/Lagos",
    val adminPinHash: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val failedPinAttempts: Int = 0,
    val pinLockUntil: Long = 0,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)