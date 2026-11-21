package com.example.attendance.domain.util

import android.util.Log.i
import java.security.SecureRandom

object QrTokenGenerator{
    private const val ALPHABET = "ABCDEFGHIJKLMNOPZRSTUVWXYZ23456789"
    private val random = SecureRandom()
    fun generate(length: Int = 10): String =
        (1..length).map { ALPHABET[random.nextInt(ALPHABET.length)] }.joinToString("")
}