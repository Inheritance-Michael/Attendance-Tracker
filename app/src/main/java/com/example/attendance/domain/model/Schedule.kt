package com.example.attendance.domain.model

import java.time.LocalTime


data class Schedule(
    val id: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val graceMinutes: Int
){
    val lateAfter: LocalTime get() = startTime.plusMinutes(graceMinutes.toLong())
}