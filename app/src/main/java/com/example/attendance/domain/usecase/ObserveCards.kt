package com.example.attendance.domain.usecase

import com.example.attendance.domain.model.PersonCard
import com.example.attendance.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCards @Inject constructor(
    private val people: PersonRepository
) {
    operator fun invoke(orgId: String): Flow<List<PersonCard>> = people.observeCards(orgId)
}