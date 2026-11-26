package com.example.attendance.domain.service

interface PinHasher {
    fun hash(pin: String): String
    fun verify(pin: String, stored: String): Boolean
}