package com.example.attendance.domain.util

interface TransactionRunner {
    suspend fun <T> inTransaction(block: suspend () -> T): T
}