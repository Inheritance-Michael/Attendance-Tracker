package com.example.attendance.domain.model

import com.example.attendance.domain.AttendanceStatus
import com.example.attendance.domain.SignInMethodType
import java.time.Instant
import java.time.LocalDate


data class AttendanceRecord(
    val id: String,
    val orgId: String,
    val personId: String,
    val timestamp: Instant,
    val date: LocalDate,
    val method: SignInMethodType,
    val status: AttendanceStatus,
)
