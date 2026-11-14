package com.example.attendance.domain.model

data class Person(
    val id: String,
    val orgId: String,
    val groupId: String? = null,
    val externalId: String? = null,
    val fullName: String,
    val role: String = "member",
    val photoPath: String? = null,
    val guardianPhone: String? = null,
    val isActive: Boolean = true
)
