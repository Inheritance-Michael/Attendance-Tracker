package com.example.attendance.domain.repository

import com.example.attendance.domain.model.Organization


interface OrganizationRepository {

    suspend fun getCurrent(): Organization?
    suspend fun create(org: Organization)
}