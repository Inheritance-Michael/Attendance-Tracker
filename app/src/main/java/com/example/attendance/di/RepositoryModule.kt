package com.example.attendance.di

import com.example.attendance.data.repository.AdminSecurityRepositoryImpl
import com.example.attendance.data.repository.AttendanceRepositoryImpi
import com.example.attendance.data.repository.DemoDataSeeder
import com.example.attendance.data.repository.GroupRepositoryImpl
import com.example.attendance.data.repository.OrganizationRepositoryImpl
import com.example.attendance.data.repository.PersonRepositoryImpl
import com.example.attendance.data.repository.RoomTransactionRunner
import com.example.attendance.data.repository.ScheduleRepositoryImpl
import com.example.attendance.domain.repository.AdminSecurityRepository
import com.example.attendance.domain.repository.AttendanceRepository
import com.example.attendance.domain.repository.DemoDataSource
import com.example.attendance.domain.repository.GroupRepository
import com.example.attendance.domain.repository.OrganizationRepository
import com.example.attendance.domain.repository.PersonRepository
import com.example.attendance.domain.repository.ScheduleRepository
import com.example.attendance.domain.util.TransactionRunner
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  RepositoryModule{
    @Binds
    @Singleton
    abstract fun bindOrganizationRepository(impl: OrganizationRepositoryImpl): OrganizationRepository

    @Binds
    @Singleton
    abstract fun bindPersonRepository(impl: PersonRepositoryImpl): PersonRepository

    @Binds
    @Singleton
    abstract fun bindScheduleRepository(impl: ScheduleRepositoryImpl): ScheduleRepository

    @Binds
    @Singleton
    abstract fun bindAttendanceRepository(impl: AttendanceRepositoryImpi): AttendanceRepository

    @Binds
    @Singleton
    abstract fun bindGroupRepository(impl: GroupRepositoryImpl): GroupRepository

    @Binds
    @Singleton
    abstract fun bindTransitionRunner(impl: RoomTransactionRunner): TransactionRunner

    @Binds
    @Singleton
    abstract fun bindAdminSecurityRepository(impl: AdminSecurityRepositoryImpl): AdminSecurityRepository

    @Binds
    @Singleton
    abstract fun bindDemoDataSource(impl: DemoDataSeeder): DemoDataSource
}