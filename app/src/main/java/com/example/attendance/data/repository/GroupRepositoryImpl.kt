package com.example.attendance.data.repository

import com.example.attendance.data.local.dao.GroupDao
import com.example.attendance.domain.repository.GroupRepository
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val dao: GroupDao
): GroupRepository {
    override suspend fun getName(groupId: String): String? {
        return dao.getById(groupId)?.name
    }

}