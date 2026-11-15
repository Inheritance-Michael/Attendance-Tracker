package com.example.attendance.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
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
    Outcome.ON_TIME -> StatusStyle(background = Color(0xFFE8F5E9), strong = Color(0xFF2E7D32), deep = Color(0xFF1B5E20), icon = Icons.Default.Check)
    Outcome.LATE -> StatusStyle(background = Color(0xFFFFF4DC), strong = Color(0xFFB26A00), deep = Color(0xFF7A4A00), icon = Icons.Default.Warning)
    Outcome.ALREADY_IN -> StatusStyle(background = Color(0xFFE3F2FD), strong = Color(0xFF1565C0), deep = Color(0xFF0D47A1), icon = Icons.Default.Info)
    Outcome.UNKNOW -> StatusStyle(background = Color(0xFFFDECEA), strong = Color(0xFFC62828), deep = Color(0xFF8E1B1B), icon = Icons.Default.Close)
}