package com.example.attendance.data.mapper


import com.example.attendance.data.local.dao.TodayRow
import com.example.attendance.data.local.entities.OrganizationEntity
import com.example.attendance.data.local.entities.PersonEntity
import com.example.attendance.data.local.entities.SchedulesEntity
import com.example.attendance.data.local.entities.relationship.AttendanceRecordEntity
import com.example.attendance.domain.AttendanceStatus
import com.example.attendance.domain.SignInMethodType
import com.example.attendance.domain.model.AttendanceEntry
import com.example.attendance.domain.model.AttendanceRecord
import com.example.attendance.domain.model.Organization
import com.example.attendance.domain.model.Person
import com.example.attendance.domain.model.Schedule
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import kotlin.text.uppercase

fun OrganizationEntity.toDomain() = Organization (
    id = id,
    name = name,
    personLabel = personLabel,
    groupLabel = groupLabel,
    zoneId = ZoneId.of(timezone)
)

fun PersonEntity.toDomain() = Person(
    id = id,
    orgId = orgId,
    groupId = groupId,
    externalId = externalId,
    fullName = fullName,
    role = role,
    photoPath = photoPath,
    guardianPhone = guardianPhone,
    isActive = isActive
)

fun SchedulesEntity.toDomain() = Schedule(
    id = id,
    startTime = LocalTime.parse(startTime),
    endTime = LocalTime.parse(endTime),
    graceMinutes = graceMinutes
)

fun Person.toEntity() = PersonEntity(
    fullName = fullName,
    role = role,
    photoPath = photoPath,
    guardianPhone = guardianPhone,
    isActive = isActive,
    orgId = orgId,
    groupId = groupId,
    externalId = externalId,
    id = id
)

fun AttendanceRecordEntity.toDomain() = AttendanceRecord(
    id = id,
    orgId = orgId,
    personId = personId,
    timestamp = Instant.ofEpochMilli(timestamp),
    date = LocalDate.parse(localDate),
    method = SignInMethodType.valueOf(method.uppercase()),
    status = AttendanceStatus.valueOf(status.uppercase())
)
fun AttendanceRecord.toEntity() = AttendanceRecordEntity(
    id = id,
    orgId = orgId,
    personId = personId,
    timestamp = timestamp.toEpochMilli(),
    localDate = date.toString(),
    method = method.name.lowercase(),
    status = status.name.lowercase()
)

fun TodayRow.toDomain() = AttendanceEntry(
    recordId = recordId,
    personName = fullName,
    timestamp = Instant.ofEpochMilli(timestamp),
    status = AttendanceStatus.valueOf(status.uppercase())
)