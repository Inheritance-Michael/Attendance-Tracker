package com.example.attendance.domain.repository

interface GroupRepository {

    suspend fun getName(groupId: String): String?
}