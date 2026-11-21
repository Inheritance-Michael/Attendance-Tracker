package com.example.attendance.domain.usecase

import com.example.attendance.domain.model.Person
import com.example.attendance.domain.repository.PersonRepository
import com.example.attendance.domain.util.QrTokenGenerator
import java.util.UUID
import javax.inject.Inject

class AddPerson @Inject constructor(
    private val people: PersonRepository
){
    suspend operator fun invoke(orgId: String, name: String): Result<Unit>{
        val clean = name.trim()
        
        if (clean.isEmpty()) return Result.failure(IllegalArgumentException("Name is required"))
        people.add(
            Person(id = UUID.randomUUID().toString(), orgId = orgId , fullName = clean),
            qrToken = QrTokenGenerator.generate()
        )
        return Result.success(Unit)
    }
}