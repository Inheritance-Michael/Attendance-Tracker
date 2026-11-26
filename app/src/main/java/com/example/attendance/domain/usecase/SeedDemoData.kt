package com.example.attendance.domain.usecase

import com.example.attendance.domain.repository.DemoDataSource
import javax.inject.Inject

class SeedDemoData @Inject constructor(
    private val demo: DemoDataSource
) {
    suspend operator fun invoke(){
        demo.seed()
    }
}