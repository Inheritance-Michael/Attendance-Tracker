package com.example.attendance.domain.model

import java.time.DayOfWeek
import java.time.LocalTime


data class Schedule(
    val id: String,
    val name: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val graceMinutes: Int,
    val days: Set<DayOfWeek>
){
    val lateAfter: LocalTime get() = startTime.plusMinutes(graceMinutes.toLong())
}