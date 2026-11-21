package com.example.attendance.domain.model

import com.example.attendance.domain.AttendanceStatus
import java.time.Instant

data class AttendanceEntry(
    val recordId: String,
    val personName: String,
    val timestamp: Instant,
    val status: AttendanceStatus
)
