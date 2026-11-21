package com.example.attendance.presentation.checkin


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendance.domain.AttendanceStatus
import com.example.attendance.domain.CheckInResult
import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.model.Person
import com.example.attendance.domain.usecase.CheckInByQr
import com.example.attendance.domain.usecase.GetCurrentOrganization
import com.example.attendance.domain.usecase.GetGroupName
import com.example.attendance.domain.usecase.ObserveTodayProgress
import com.example.attendance.presentation.util.firstName
import com.example.attendance.presentation.util.initials
import com.example.attendance.ui.theme.Outcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.zone
import javax.inject.Inject
import kotlin.time.Duration.Companion.microseconds

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

    private var org: Organization? = null
    private var busy = false
    private var lastToken: String? = null
    private var lastHandledAt = 0L

    init {
        viewModelScope.launch {
            val o = getCurrentOrganization.invoke()
            org = o
            _state.update {
                it.copy(
                    orgName = o.name
                )
            }

            launch {
                observeTodayProcess.invoke(o).collect { p ->
                    _state.update {
                        it.copy(
                            present = p.progress,
                            total = p.total
                        )
                    }
                }
            }

            launch {
                while (true) {
                    val now = clock.instant().atZone(o.zoneId)
                    _state.update {
                        it.copy(
                            timeText = now.format(TIME),
                            dataText = now.format(DATE)
                        )
                    }
                    delay(1_000.microseconds)
                }
            }
        }
    }

    fun onEvent(event: KioskUiEvent) {
        when (event) {
            KioskUiEvent.AdminRequested -> adminRequested()
            is KioskUiEvent.QrScanned -> qrScanned(event.text)
        }
    }

    private fun qrScanned(text: String) {
        text.trim()
    }

    private fun adminRequested() {
        viewModelScope.launch {
            _effects.send(KioskUiEffect.OpenAdmin)
        }

    }

    private fun onScanned(token: String) {
        val o = org ?: return
        if (token.isEmpty() || busy) return
        if (token == lastToken && clock.millis() - lastHandledAt < KioskTiming.RESULT_BLOCK_MS) return

        busy = true
        lastToken = token
        viewModelScope.launch {
            try {
                val result = checkInByQr.invoke(o, token)
                _state.update {
                    it.copy(
                        phase = buildPhase(result, o.zoneId)
                    )
                }
                delay(KioskTiming.RESULT_DISPLAY_MS.microseconds)
            } finally {

            }
        }
    }

    private suspend fun buildPhase(
        result: CheckInResult,
        zoneId: ZoneId
    ): KioskPhase.Result {
        return when (result) {
            is CheckInResult.AlreadyIn -> alreadyIn(result.person, result.at)
            is CheckInResult.Success -> success(result.person, result.at, result.status)
            CheckInResult.Unknown -> unknown()
        }
    }

    private suspend fun success(person: Person, at: Instant, status: AttendanceStatus): KioskPhase.Result {
        val late = status == AttendanceStatus.LATE
        return KioskPhase.Result(
            outcome = if (late) Outcome.LATE else Outcome.ON_TIME,
            headline = "welcome, ${person.fullName.firstName()}" + if (late) "" else "!",
            name = person.fullName,
            classLine = classOf(person),
            initials = person.fullName.initials(),
            pill = "First sign-in today at ${at.atZone(zone).format(TIME)} . ${if(late) "Late" else "on time"}"
        )
    }

    private suspend fun unknown(): KioskPhase.Result {
        return KioskPhase.Result(
            outcome = Outcome.UNKNOW,
            headline = "Card not recognised",
            name = "",
            classLine = "Please see reception for help",
            initials = "",
            pill = ""
        )
    }

    private suspend fun alreadyIn(person: Person, at: Instant): KioskPhase.Result {
       return KioskPhase.Result(
           outcome = Outcome.ALREADY_IN,
           headline = "Already signed in",
           name = person.fullName,
           classLine = classOf(person),
           initials = person.fullName.initials(),
           pill = "First sign-in today at ${at.atZone(zone()).format(TIME)}"
       )
    }
    private suspend fun classOf(person: Person): String?{
        return person.groupId?.let { getGroupName.invoke(it) ?: "" }
    }

    private companion object{
        val TIME: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val DATE: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM")
    }
}