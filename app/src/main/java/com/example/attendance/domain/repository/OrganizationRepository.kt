package com.example.attendance.domain.repository

import com.example.attendance.domain.model.Organization


interface OrganizationRepository {

    suspend fun getOrCreateDefault(): Organization
}