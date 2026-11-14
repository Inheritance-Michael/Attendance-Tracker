package com.example.attendance.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.attendance.data.local.dao.AttendanceDao
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
import com.example.attendance.data.local.entities.relationship.AttendanceRecordEntity

@Database(
    entities = [
        OrganizationEntity::class,
        AttendanceRecordEntity::class,
        GroupEntity::class,
        PersonEntity::class,
        CredentialEntity::class,
        SchedulesEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun organizationDao(): OrganizationDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun groupDao(): GroupDao
    abstract fun personDao(): PersonDao
    abstract fun credentialDao(): CredentialDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase = instance ?: synchronized(this){
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "attendance.db"
            ).build().also { instance = it }
        }
    }

}