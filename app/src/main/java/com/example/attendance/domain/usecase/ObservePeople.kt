package com.example.attendance.domain.usecase

import com.example.attendance.domain.model.Person
import com.example.attendance.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePeople @Inject constructor(
    private val people: PersonRepository
) {
    operator fun invoke(orgId: String): Flow<List<Person>>{
        return people.observeActive(orgId)
    }
}