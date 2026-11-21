package com.example.attendance.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.AttendanceStatus
import com.example.attendance.domain.CheckInResult
import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.usecase.AddPerson
import com.example.attendance.domain.usecase.CheckInByQr
import com.example.attendance.domain.usecase.GetCurrentOrganization
import com.example.attendance.domain.usecase.GetQrToKen
import com.example.attendance.domain.usecase.ObserveAbsent
import com.example.attendance.domain.usecase.ObservePeople
import com.example.attendance.domain.usecase.ObserveToday
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getOrganization: GetCurrentOrganization,
    private val observeAbsent: ObserveAbsent,
    private val observeToday: ObserveToday,
    private val observePeople: ObservePeople,
    private val addPerson: AddPerson,
    private val checkInByQr: CheckInByQr,
    private val getQrToKen: GetQrToKen
): ViewModel() {

    private val _state = MutableStateFlow(HomeScreenUiState())
    val state: StateFlow<HomeScreenUiState> = _state.asStateFlow()

    private val _effects = Channel<HomeUIEffect>(Channel.BUFFERED)
    val effects: Flow<HomeUIEffect> = _effects.receiveAsFlow()

    private var org: Organization? = null
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    init {
        load()
    }

    private fun load(){
        viewModelScope.launch {
            val o = getOrganization.invoke()
            org = o
            _state.update {
                it.copy(
                    isLoading = false,
                    organizationName = o.name
                )
            }

            launch {
                observePeople.invoke(o.id).collect { list ->
                    _state.update { s -> s.copy(
                        people = list.map {
                            PersonItem(it.id, it.fullName)
                        }
                    ) }
                }
            }

            launch {
                observeToday.invoke(o).collect {
                    list -> _state.update { it ->
                        it.copy(
                            present = list.map {
                                PresentItem(
                                    id = it.recordId,
                                    name = it.personName,
                                    timeText = it.timestamp.atZone(o.zoneId).format(timeFormat),
                                    isLate = it.status == AttendanceStatus.LATE
                                )
                            }
                        )
                    }
                }
            }

            launch {
                observeAbsent.invoke(o).collect { list ->
                    _state.update { it ->
                        it.copy(
                            absent = list.map { PersonItem(it.id, it.fullName) }
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeUiEvent){
        when(event){
            HomeUiEvent.AddClicked -> addNewPerson()
            is HomeUiEvent.OnNameChange -> nameChange(event.newName)
            is HomeUiEvent.SimulateScanClicked -> simulateScan(event.personId)
        }
    }

    private fun simulateScan(personId: String) {
        val o = org ?: return
        viewModelScope.launch {
            val toKen = getQrToKen.invoke(personId)
            val result = if(toKen == null) CheckInResult.Unknown else checkInByQr(o, toKen)
            _effects.send(HomeUIEffect.ShowMessage(result.toMessage()))
        }
    }

    private fun nameChange(newName: String){
        _state.update { it.copy(
            newName = newName
        ) }
    }

    private fun addNewPerson(){
        val o = org ?: return
        viewModelScope.launch {
            addPerson.invoke(o.id, _state.value.newName)
                .onSuccess { _state.update { it.copy(newName = "") } }
                .onFailure { _effects.send(HomeUIEffect.ShowMessage(it.message ?: "Could not add person")) }
        }
    }

    fun CheckInResult.toMessage(): String = when (this) {
        is CheckInResult.Success ->
            "${person.fullName}: ${if (status == AttendanceStatus.LATE) "LATE" else "on time"}"
        is CheckInResult.AlreadyIn -> "${person.fullName} already signed in"
        CheckInResult.Unknown -> "Card not recognised"
    }


}