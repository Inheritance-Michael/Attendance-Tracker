package com.example.attendance.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.attendance.data.entities.OrganizationEntity

@Dao
interface OrganizationDao{

    @Upsert
    suspend fun upsert(org: OrganizationEntity)

    @Query(value = "SELECT * FROM organization LIMIT 1")
    suspend fun getFirst(): OrganizationEntity?

    @Query(value = "SELECT * FROM organization WHERE id = :id")
    suspend fun getById(id: String): OrganizationEntity?
}

