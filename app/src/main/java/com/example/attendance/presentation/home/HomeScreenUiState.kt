package com.example.attendance.presentation.home

data class PersonItem(
    val id: String,
    val name: String
)

data class PresentItem(
    val id: String,
    val name: String,
    val timeText: String,
    val isLate: Boolean
)

data class HomeScreenUiState(
    val isLoading: Boolean = true,
    val organizationName: String = "",
    val people: List<PersonItem> = emptyList(),
    val present: List<PresentItem> = emptyList(),
    val absent: List<PersonItem> = emptyList(),
    val newName: String = "",
)

