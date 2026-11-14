package com.example.attendance.data.local.dao


import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.attendance.data.local.entities.CredentialEntity

@Dao
interface CredentialDao {

    @Upsert
    suspend fun upsert(credential: CredentialEntity)

    @Query("SELECT * FROM credentials WHERE type = :type AND value = :value AND isActive = 1 LIMIT 1")
    suspend fun findActive(type: String, value: String): CredentialEntity?

    @Query("SELECT * FROM credentials WHERE personId = :personId AND type = :type AND isActive = 1 LIMIT 1")
    suspend fun findPerson(personId: String, type: String): CredentialEntity?
}