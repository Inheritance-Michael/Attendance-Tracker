package com.example.attendance.data.local.dao.util

data class PinRow(
    val hash: String,
    val failedAttempts: Int,
    val lockedUntil: Long
)

