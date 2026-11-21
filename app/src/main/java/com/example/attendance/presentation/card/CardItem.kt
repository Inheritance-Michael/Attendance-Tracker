package com.example.attendance.presentation.card

import com.example.attendance.domain.util.QrMatrix

data class CardItem(
    val id: String,
    val name: String,
    val token: String,
    val qr: QrMatrix
)
