package com.example.attendance.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


enum class Outcome{
    ON_TIME,
    LATE,
    ALREADY_IN,
    UNKNOW
}

object AppColors{
    val Primary = Color(0xFF1F4E8C)
    val Ink = Color(0xFF14213D)
    val Muted = Color(0xFF44546A)
    val Surface = Color(0xFFF5F7FA)
    val CameraDark = Color(0xFF0F1B2D)
}

data class StatusStyle(
    val background: Color,
    val strong: Color,
    val deep: Color,
    val icon: ImageVector
)

fun Outcome.style(): StatusStyle = when(this){
    Outcome.ON_TIME -> StatusStyle(background = Color(0xFFE8F5E9), strong = Color(0xFF2E7D32), )
    Outcome.LATE -> TODO()
    Outcome.ALREADY_IN -> TODO()
    Outcome.UNKNOW -> TODO()
}