package com.example.attendance.data.repository

import androidx.room.withTransaction
import com.example.attendance.data.local.AppDatabase
import com.example.attendance.data.local.dao.CredentialDao
import com.example.attendance.data.local.dao.PersonDao
import com.example.attendance.data.local.entities.CredentialEntity
import com.example.attendance.data.mapper.toDomain
import com.example.attendance.data.mapper.toEntity
import com.example.attendance.domain.model.Person
import com.example.attendance.domain.model.PersonCard
import com.example.attendance.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PersonRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private  val personDao: PersonDao,
    private  val credentialDao: CredentialDao
) : PersonRepository {
    override fun observeActiveCount(orgId: String): Flow<Int> {
        return personDao.observeActiveCount(orgId)
    }

    override fun observeCards(orgId: String): Flow<List<PersonCard>> {
        return personDao.observeWithQrToken(orgId).map{ rows ->
            rows.map {
                PersonCard(
                    it.person.toDomain(),
                    it.token
                )
            }
        }
    }

    override fun observeActive(orgId: String): Flow<List<Person>> {
        return personDao.observeActive(orgId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getById(id: String): Person? {
        return personDao.getById(id)?.toDomain()
    }

    override suspend fun findByQrToken(
        orgId: String,
        token: String
    ): Person? {
        return personDao.findByCredential(orgId = orgId, type = "Qr",  value = token)?.toDomain()
    }

    override suspend fun getQrToken(personId: String): String? {
        return credentialDao.findPerson(personId = personId, type = "qr")?.value
    }

    override suspend fun add(
        person: Person,
        qrToken: String
    ) {
      db.withTransaction {
          personDao.upsert(person.toEntity())
          credentialDao.upsert(
              CredentialEntity(
                  type = "Qr",
                  value = qrToken,
                  orgId = person.orgId,
                  personId = person.id,
              )
          )
      }
    }

}