package com.example.attendance.domain.usecase

import com.example.attendance.domain.service.QrCodeGenerator
import com.example.attendance.domain.util.QrMatrix
import javax.inject.Inject

class GenerateQrMatrix @Inject constructor(
    private val qrMatrix: QrCodeGenerator
) {
    operator fun invoke(content: String): QrMatrix = qrMatrix.generate(content)
}