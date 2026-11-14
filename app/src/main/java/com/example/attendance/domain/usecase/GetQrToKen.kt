package com.example.attendance.domain.usecase

import com.example.attendance.domain.repository.PersonRepository
import javax.inject.Inject

class GetQrToKen @Inject constructor(
    private val people: PersonRepository,
) {
    suspend operator fun invoke(personId: String): String?{
        return people.getQrToken(personId)
    }
}