package com.example.attendance.presentation.checkin

sealed interface KioskUiEffect {
    data object OpenAdmin: KioskUiEffect
}