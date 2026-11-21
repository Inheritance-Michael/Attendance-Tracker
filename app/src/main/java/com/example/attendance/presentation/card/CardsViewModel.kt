package com.example.attendance.presentation.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.usecase.GenerateQrMatrix
import com.example.attendance.domain.usecase.GetCurrentOrganization
import com.example.attendance.domain.usecase.ObserveCards
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardsViewModel @Inject constructor(
    private val getCurrentOrganization: GetCurrentOrganization,
    private val observeCards: ObserveCards,
    private val generateQrMatrix: GenerateQrMatrix
): ViewModel() {
    private val _state = MutableStateFlow(CardUiState())
    val state: StateFlow<CardUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val org = getCurrentOrganization.invoke()

            observeCards.invoke(org.id)
                .map {
                    cards -> cards.map { CardItem(
                    it.person.id,
                    it.person.fullName,
                    it.qrToKen,
                     generateQrMatrix(it.qrToKen)
                    ) }
                }
                .flowOn(Dispatchers.Default)
                .collect { item -> _state.value = CardUiState(isLoading = false, cards = item) }
        }
    }
}