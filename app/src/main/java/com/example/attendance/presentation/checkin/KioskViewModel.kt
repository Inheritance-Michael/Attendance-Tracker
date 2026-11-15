package com.example.attendance.presentation.checkin

import androidx.compose.runtime.FloatState
import androidx.lifecycle.ViewModel
import com.example.attendance.domain.usecase.CheckInByQr
import com.example.attendance.domain.usecase.GetCurrentOrganization
import com.example.attendance.domain.usecase.GetGroupName
import com.example.attendance.domain.usecase.ObserveTodayProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.Clock
import javax.inject.Inject

@HiltViewModel
class KioskViewModel @Inject constructor(
    private val getCurrentOrganization: GetCurrentOrganization,
    private  val observeTodayProcess: ObserveTodayProgress,
    private val checkInByQr: CheckInByQr,
    private val getGroupName: GetGroupName,
    private val clock: Clock
): ViewModel() {
    private val _state = MutableStateFlow(KiosUiState())
    val state: StateFlow<KiosUiState> = _state.asStateFlow()

    private val _effects = Channel<KioskUiEffect>(Channel.BUFFERED)
    val effects: Flow<KioskUiEffect> = _effects.receiveAsFlow()


    fun onEvent(event: KioskUiEvent){
        when(event){
            KioskUiEvent.AdminRequested -> TODO()
            is KioskUiEvent.QrScanned -> TODO()
        }
    }
}