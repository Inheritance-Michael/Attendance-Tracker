package com.example.attendance.domain.model

data class NewOrganisation(
    val name: String,
    val personLabel: String,
    val groupLabel: String,
    val timeZone: String,
    val pin: String,
    val confirmPin: String
)
