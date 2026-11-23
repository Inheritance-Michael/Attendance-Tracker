package com.example.attendance.di

import android.content.Context
import androidx.room.Room
import com.example.attendance.data.local.AppDatabase
import com.example.attendance.data.local.dao.AttendanceDao
import com.example.attendance.data.local.dao.CredentialDao
import com.example.attendance.data.local.dao.GroupDao
import com.example.attendance.data.local.dao.OrganizationDao
import com.example.attendance.data.local.dao.PersonDao
import com.example.attendance.data.local.dao.ScheduleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "attendance_db").build()
    }

    @Provides
    fun provideOrganizationDao(db: AppDatabase): OrganizationDao = db.organizationDao()

    @Provides
    fun provideAttendanceDao(db: AppDatabase): AttendanceDao = db.attendanceDao()

    @Provides
    fun provideGroupDao(db: AppDatabase): GroupDao = db.groupDao()

    @Provides
    fun providePersonDao(db: AppDatabase): PersonDao = db.personDao()

    @Provides
    fun provideCredentialDao(db: AppDatabase): CredentialDao = db.credentialDao()

    @Provides
    fun provideScheduleDao(db: AppDatabase): ScheduleDao = db.scheduleDao()
}
