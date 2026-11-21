package com.example.attendance.domain.service


import com.example.attendance.domain.util.QrMatrix

interface QrCodeGenerator{
    fun generate(content: String): QrMatrix
}