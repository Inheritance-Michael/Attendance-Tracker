package com.example.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.attendance.data.local.entities.PersonEntity
import com.example.attendance.data.local.entities.PersonWithToken
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Upsert
    suspend fun upsert(person: PersonEntity)

    @Query(value = "SELECT * FROM people WHERE id = :id")
    suspend fun getById(id: String): PersonEntity?

    @Query(value = "SELECT * FROM people WHERE orgId = :orgId AND isActive = 1 ORDER BY fullName")
    fun observeActive(orgId: String): Flow<List<PersonEntity>>

    @Query("""
    SELECT p.* FROM people p
    JOIN credentials c ON c.personId = p.id
    WHERE c.orgId = :orgId AND c.type = :type AND c.value = :value
      AND c.isActive = 1 AND p.isActive = 1
    LIMIT 1
""")
    suspend fun findByCredential(orgId: String, type: String, value: String): PersonEntity?

    @Query("SELECT COUNT(*) FROM people WHERE orgId = :orgId AND isActive = 1")
    fun observeActiveCount(orgId: String): Flow<Int>

    @Query("""
    SELECT p.*, c.value AS token FROM people p
    JOIN credentials c ON c.personId = p.id AND c.type = 'qr' AND c.isActive = 1
    WHERE p.orgId = :orgId AND p.isActive = 1
    ORDER BY p.fullName
""")
    fun observeWithQrToken(orgId: String): Flow<List<PersonWithToken>>
}