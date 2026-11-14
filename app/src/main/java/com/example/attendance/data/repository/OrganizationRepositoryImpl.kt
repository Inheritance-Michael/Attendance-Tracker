package com.example.attendance.data.repository

import com.example.attendance.data.local.dao.OrganizationDao
import com.example.attendance.data.mapper.toDomain
import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.repository.OrganizationRepository
import javax.inject.Inject

class OrganizationRepositoryImpl @Inject constructor(
    private val dao: OrganizationDao,
    private val seeder: DemoDataSeeder
): OrganizationRepository{
    override suspend fun getOrCreateDefault(): Organization {
        val existing = dao.getFirst() ?: dao.getById(seeder.seedIfEmpty())
        return existing!!.toDomain()
    }
}