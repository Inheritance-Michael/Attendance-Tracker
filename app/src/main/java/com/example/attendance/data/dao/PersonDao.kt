package com.example.attendance.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.attendance.data.entities.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Upsert
    suspend fun upsert(person: PersonEntity)

    @Query(value = "SELECT * FROM person WHERE id = :id")
    suspend fun getById(id: String): PersonEntity?

    @Query(value = "SELECT * FROM person WHERE orgId = :orgId AND isActive = 1 ORDER BY fullName")
    fun observeActive(orgId: String): Flow<List<PersonEntity>>
}