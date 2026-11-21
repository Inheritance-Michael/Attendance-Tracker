package com.example.attendance.presentation.checkin



data class KiosUiState(
    val orgName: String = "",
    val timeText: String = "--:--",
    val dateText: String = "",
    val present: Int = 0,
    val total: Int = 0,
    val phase: KioskPhase = KioskPhase.Scanning
)
