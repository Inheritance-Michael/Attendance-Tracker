package com.example.attendance.domain.usecase

import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.repository.OrganizationRepository
import javax.inject.Inject

class IsSetUp @Inject constructor(
    private val organization: OrganizationRepository
) {
    suspend operator fun invoke(): Boolean {
        return organization.getCurrent() != null
    }
}