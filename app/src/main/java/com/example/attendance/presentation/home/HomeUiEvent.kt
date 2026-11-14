package com.example.attendance.presentation.home

sealed interface HomeUiEvent {
    data class OnNameChange(val newName: String) : HomeUiEvent
    data object AddClicked: HomeUiEvent
    data class SimulateScanClicked(val personId: String): HomeUiEvent
}

sealed interface HomeUIEffect {
    data class ShowMessage(val text: String): HomeUIEffect
}
