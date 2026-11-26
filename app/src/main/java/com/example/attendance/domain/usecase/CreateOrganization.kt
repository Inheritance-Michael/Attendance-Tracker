package com.example.attendance.domain.usecase

import com.example.attendance.domain.repository.OrganizationRepository
import javax.inject.Inject

class CreateOrganization @Inject constructor(
    private val organization: OrganizationRepository
) {
}