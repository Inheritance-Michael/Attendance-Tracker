package com.example.attendance.domain

import com.example.attendance.domain.model.Person
import java.time.Instant

sealed interface CheckInResult {
    data class Success(val person: Person, val status: AttendanceStatus, val at: Instant): CheckInResult
    data class AlreadyIn(val person: Person, val at: Instant): CheckInResult
    data object Unknown: CheckInResult

}