package com.example.attendance.domain.util

class QrMatrix(
    val size: Int,
    private val modules: BooleanArray
) {
    fun isDark(x: Int, y: Int): Boolean = modules[y * size + x]
}