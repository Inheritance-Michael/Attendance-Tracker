package com.example.attendance.data.service

import com.example.attendance.domain.service.QrCodeGenerator
import com.example.attendance.domain.util.QrMatrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

class ZxingQrCodeGenerator: QrCodeGenerator {
    override fun generate(content: String): QrMatrix {
        val hints = mapOf(
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.H,
            EncodeHintType.MARGIN to 0
        )

        val bits = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, 0,0, hints)
        val size = bits.width
        return QrMatrix(size, BooleanArray(size * size){ i-> bits.get(i % size, i/ size)})
    }
}