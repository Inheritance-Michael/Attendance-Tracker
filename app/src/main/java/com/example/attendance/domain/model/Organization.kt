package com.example.attendance.domain.model

import java.time.ZoneId

data class Organization(
    val id: String,
    val name: String,
    val personLabel: String,
    val groupLabel: String,
    val zoneId: ZoneId
)
