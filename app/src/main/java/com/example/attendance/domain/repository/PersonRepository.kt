package com.example.attendance.domain.repository


import com.example.attendance.domain.model.Person
import com.example.attendance.domain.model.PersonCard
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    fun observeActiveCount(orgId: String): Flow<Int>
    fun observeCards(orgId: String): Flow<List<PersonCard>>
    fun observeActive(orgId: String): Flow<List<Person>>
    suspend fun getById(id: String): Person?
    suspend fun findByQrToken(orgId: String, token: String): Person?
    suspend fun getQrToken(personId: String): String?
    suspend fun add(person: Person, qrToken: String)

}