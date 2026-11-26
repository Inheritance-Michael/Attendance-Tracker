package com.example.attendance.domain.model.util

import com.example.attendance.domain.model.Organization

sealed interface CreateOrganizationResult {
    data class Create(val organization: Organization): CreateOrganizationResult
    data class  Invalid(val error: Set<SetUpError>): CreateOrganizationResult
}