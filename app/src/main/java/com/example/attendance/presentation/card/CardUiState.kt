package com.example.attendance.presentation.card

data class CardUiState(
    val isLoading: Boolean = true,
    val cards: List<CardItem> = emptyList()
)
