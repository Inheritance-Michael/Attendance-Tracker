package com.example.attendance.di

import com.example.attendance.data.service.Pbkdf2PinHasher
import com.example.attendance.data.service.ZxingQrCodeGenerator
import com.example.attendance.domain.service.PinHasher
import com.example.attendance.domain.service.QrCodeGenerator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    @Singleton
    abstract fun bindQrcodeGenerator(impl: ZxingQrCodeGenerator): QrCodeGenerator

    @Binds
    @Singleton
    abstract fun bindPinHasher(impl: Pbkdf2PinHasher): PinHasher
}