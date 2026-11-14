package com.example.attendance.data.local.entities

import androidx.room.Embedded

data class PersonWithToken(
    @Embedded val person: PersonEntity,
    val token: String
)
