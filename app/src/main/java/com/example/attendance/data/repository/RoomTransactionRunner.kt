package com.example.attendance.data.repository

import androidx.room.withTransaction
import com.example.attendance.data.local.AppDatabase
import com.example.attendance.domain.util.TransactionRunner
import javax.inject.Inject

class RoomTransactionRunner @Inject constructor(
    private val db: AppDatabase
): TransactionRunner {
    override suspend fun <T> inTransaction(block: suspend () -> T): T {
       return db.withTransaction {  block() }
    }
}