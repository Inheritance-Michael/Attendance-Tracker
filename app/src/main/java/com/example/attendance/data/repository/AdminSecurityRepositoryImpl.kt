package com.example.attendance.data.repository

import com.example.attendance.data.local.dao.OrganizationDao
import com.example.attendance.domain.model.PinState
import com.example.attendance.domain.repository.AdminSecurityRepository
import java.time.Instant
import javax.inject.Inject

class AdminSecurityRepositoryImpl @Inject constructor(
    private val dao: OrganizationDao
): AdminSecurityRepository {
    override suspend fun getPinState(orgId: String): PinState {
        val row = dao.getPinRow(orgId)
        return PinState(
            hash = row.hash,
            failedAttempt = row.failedAttempts,
            lockedUntil = row.lockedUntil.takeIf { it > 0 }?.let { Instant.ofEpochMilli(it) }
        )
    }

    override suspend fun setPin(orgId: String, hash: String) {
        dao.setPin(orgId, hash, System.currentTimeMillis())
    }

    override suspend fun recordFailure(
        orgId: String,
        failedAttempt: Int,
        lockUntil: Instant?
    ) {
       dao.setFailures(orgId, failedAttempt, lockUntil?.toEpochMilli() ?: 0L)
    }

    override suspend fun resetFailures(orgId: String) {
        dao.setFailures(orgId, 0, 0L)
    }
}