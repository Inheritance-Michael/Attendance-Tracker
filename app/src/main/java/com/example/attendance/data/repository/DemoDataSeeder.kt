package com.example.attendance.data.repository

import com.example.attendance.data.local.dao.CredentialDao
import com.example.attendance.data.local.dao.GroupDao
import com.example.attendance.data.local.dao.OrganizationDao
import com.example.attendance.data.local.dao.PersonDao
import com.example.attendance.data.local.dao.ScheduleDao
import com.example.attendance.data.local.entities.CredentialEntity
import com.example.attendance.data.local.entities.GroupEntity
import com.example.attendance.data.local.entities.OrganizationEntity
import com.example.attendance.data.local.entities.PersonEntity
import com.example.attendance.data.local.entities.SchedulesEntity
import com.example.attendance.domain.util.QrTokenGenerator
import javax.inject.Inject

class DemoDataSeeder @Inject constructor(
    private val orgDao: OrganizationDao,
    private val scheduleDao: ScheduleDao,
    private val groupDao: GroupDao,
    private val personDao: PersonDao,
    private val credentialDao: CredentialDao
) {
    suspend fun seedIfEmpty(): String {
        orgDao.getFirst()?.let { return it.id }

        val org = OrganizationEntity(name = "Demo School")
        orgDao.upsert(org)

        val alwaysLate = SchedulesEntity(
            orgId = org.id,
            name = "Test: always late",
            startTime = "00:00",
            endTime = "23:59",
            graceMinutes = 0,
        )

        val alwaysOnTime = SchedulesEntity(
            orgId = org.id,
            name = "Test: always late",
            startTime = "00:00",
            endTime = "23:59",
            graceMinutes = 0,
        )

        scheduleDao.upsert(alwaysOnTime)
        scheduleDao.upsert(alwaysLate)

        val lateGroup = GroupEntity(
            orgId = org.id,
            name = "late Test Class",
            scheduleId = alwaysLate.id,
        )

        val onTimeGroup = GroupEntity(
            orgId = org.id,
            name = "On-time Test",
            scheduleId = alwaysOnTime.id,
        )
        groupDao.upsert(lateGroup)
        groupDao.upsert(onTimeGroup)

        val people = listOf<Pair<String, String?>>(
            "Ada Obi" to lateGroup.id,
            "Tunde Bello" to lateGroup.id,
            "Chidi Eze" to onTimeGroup.id,
            "Ngozi Okafor" to onTimeGroup.id,
            "Ibrahim Musa" to null
        )

        people.forEach { (name, groupId) ->
            val person = PersonEntity(orgId = org.id, groupId = groupId, fullName = name)
            personDao.upsert(person)
            credentialDao.upsert(
                CredentialEntity(
                    orgId = org.id,
                    personId = person.id,
                    type = "qr",
                    value = QrTokenGenerator.generate()
                )
            )
        }

        return org.id
    }
}