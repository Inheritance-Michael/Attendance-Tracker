package com.example.attendance.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.attendance.data.entities.GroupEntity

@Dao
interface GroupDao {

    @Upsert
    suspend fun upsert(group: GroupEntity)

    @Query(value = "SELECT * FROM `groups` WHERE id = :id")
    suspend fun getById(id: String): GroupEntity?
}