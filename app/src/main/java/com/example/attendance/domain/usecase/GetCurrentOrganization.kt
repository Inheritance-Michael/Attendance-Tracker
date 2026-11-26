package com.example.attendance.domain.usecase

import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.repository.OrganizationRepository
import javax.inject.Inject

class GetCurrentOrganization @Inject constructor(
    private val organization: OrganizationRepository
) {
    suspend operator fun invoke(): Organization  {
        return checkNotNull(organization.getCurrent()) {"No Organization has been Set ip yet"}
    }
}