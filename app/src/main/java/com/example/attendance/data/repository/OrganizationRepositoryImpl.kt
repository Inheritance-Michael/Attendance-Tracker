package com.example.attendance.data.repository

import com.example.attendance.data.local.dao.OrganizationDao
import com.example.attendance.data.mapper.toDomain
import com.example.attendance.data.mapper.toEntity
import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.repository.OrganizationRepository
import javax.inject.Inject

class OrganizationRepositoryImpl @Inject constructor(
    private val dao: OrganizationDao,
): OrganizationRepository{

    override suspend fun getCurrent(): Organization? {
        return dao.getFirst()?.toDomain()
    }

    override suspend fun create(org: Organization) {
        dao.upsert(org.toEntity())
    }
}