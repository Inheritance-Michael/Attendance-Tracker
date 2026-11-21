package com.example.attendance.domain.usecase


import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.model.TodayProgress
import com.example.attendance.domain.repository.AttendanceRepository
import com.example.attendance.domain.repository.PersonRepository
import com.example.attendance.domain.util.todayFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import java.time.Clock
import javax.inject.Inject


class ObserveTodayProgress @Inject constructor(
    private val attendance: AttendanceRepository,
    private val  people: PersonRepository,
    private val clock: Clock
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(org: Organization): Flow<TodayProgress> =
        clock.todayFlow(org.zoneId).flatMapLatest { today ->
            combine(
                attendance.observePresentCount(org.id, today),
                people.observeActiveCount(org.id)
            ){present, total ->
                TodayProgress(present, total)
            }
        }
}