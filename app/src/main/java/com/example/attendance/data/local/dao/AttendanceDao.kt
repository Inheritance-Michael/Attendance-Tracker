package com.example.attendance.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.attendance.data.local.entities.PersonEntity
import com.example.attendance.data.local.entities.relationship.AttendanceRecordEntity
import kotlinx.coroutines.flow.Flow

data class TodayRow(
    val recordId: String,
    val fullName: String,
    val timestamp: Long,
    val status: String
)

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insert(record: AttendanceRecordEntity)

    @Query("SELECT * FROM attendance_records WHERE personId = :personId AND localDate = :date LIMIT 1")
    suspend fun firstForDay(personId: String, date: String): AttendanceRecordEntity?

    @Query(
        """
        SELECT a.id AS recordId, p.fullName AS fullName, a.timestamp AS timestamp, a.status AS status
        FROM attendance_records a
        JOIN people p ON p.id = a.personId
        WHERE a.orgId = :orgId AND a.localDate = :date AND a.direction = 'in'
        ORDER BY a.timestamp DESC
        """
    )
    fun observeToday(orgId: String, date: String): Flow<List<TodayRow>>

    @Query(
        """
        SELECT * FROM people
        WHERE orgId = :orgId AND isActive = 1
          AND id NOT IN (
              SELECT personId FROM attendance_records
              WHERE orgId = :orgId AND localDate = :date
          )
        ORDER BY fullName
        """
    )
    fun observeAbsent(orgId: String, date: String): Flow<List<PersonEntity>>

    @Query("""
    SELECT COUNT(DISTINCT personId) FROM attendance_records
    WHERE orgId = :orgId AND localDate = :date AND direction = 'in'
""")
    fun observePresentCount(orgId: String, date: String): Flow<Int>

}