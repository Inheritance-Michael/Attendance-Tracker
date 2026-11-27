package com.example.attendance.domain.repository

import com.example.attendance.domain.model.PinState
import java.time.Instant

interface AdminSecurityRepository {
    suspend fun getPinState(orgId: String): PinState
    suspend fun setPin(orgId: String, hash: String)
    suspend fun recordFailure(orgId: String, failedAttempt: Int, lockUntil: Instant?)
    suspend fun resetFailures(orgId: String)
}