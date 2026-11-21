package com.example.attendance.domain.util


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId
import kotlin.time.Duration.Companion.milliseconds

fun Clock.todayFlow(zone: ZoneId): Flow<LocalDate> = flow{
    while(true){
        emit(LocalDate.now(withZone(zone)))
        delay(30_000.milliseconds)
    }
}.distinctUntilChanged()