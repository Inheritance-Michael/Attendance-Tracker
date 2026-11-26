package com.example.attendance.domain.model.util

import java.time.Instant


sealed interface PinResult {
    data object Success: PinResult
    data class Wrong(val attemptLeft: Int): PinResult
    data class Locked(val until: Instant): PinResult
}