package com.example.attendance.domain.model

import java.time.Instant


data class PinState(
    val hash: String,
    val failedAttempt: Int,
    val lockedUntil: Instant?
)
