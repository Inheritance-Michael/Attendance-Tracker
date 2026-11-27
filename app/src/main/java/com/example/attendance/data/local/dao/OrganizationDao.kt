package com.example.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.attendance.data.local.dao.util.PinRow
import com.example.attendance.data.local.entities.OrganizationEntity

@Dao
interface OrganizationDao{

    @Upsert
    suspend fun upsert(org: OrganizationEntity)

    @Query(value = "SELECT * FROM organization LIMIT 1")
    suspend fun getFirst(): OrganizationEntity?

    @Query(value = "SELECT * FROM organization WHERE id = :id")
    suspend fun getById(id: String): OrganizationEntity?

    @Query("""
          SELECT adminPinHash AS hash, failedPinAttempts AS failedAttempts, pinLockedUntil AS lockedUntil
    FROM organization WHERE id = :id
    """)
    suspend fun getPinRow(id: String): PinRow

    @Query("""
    UPDATE organization
    SET adminPinHash = :hash, failedPinAttempts = 0, pinLockedUntil = 0, updatedAt = :now
    WHERE id = :id
""")
    suspend fun setPin(id: String, hash: String, now: Long)

    @Query("UPDATE organization SET failedPinAttempts = :attempts, pinLockedUntil = :lockedUntil WHERE id = :id")
    suspend fun setFailures(id: String, attempts: Int, lockedUntil: Long)
}

