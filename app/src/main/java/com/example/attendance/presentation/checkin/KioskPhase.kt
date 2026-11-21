package com.example.attendance.presentation.checkin

import com.example.attendance.ui.theme.Outcome

sealed interface KioskPhase{
    data object Scanning: KioskPhase
    data class Result(
        val outcome: Outcome,
        val headline: String,
        val name: String,
        val classLine: String?,
        val initials: String,
        val pill: String
    ): KioskPhase
}