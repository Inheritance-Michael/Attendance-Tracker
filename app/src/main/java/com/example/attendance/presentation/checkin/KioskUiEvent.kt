package com.example.attendance.presentation.checkin

sealed interface KioskUiEvent {
    data class QrScanned(val text: String): KioskUiEvent
    data object AdminRequested: KioskUiEvent
}