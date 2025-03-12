package com.example.upload.di

import com.example.upload.data.UploadRepositoryImpl
import com.example.upload.domain.UploadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UploadModule {
    @Binds
    abstract fun bindRepository(
        uploadRepositoryImpl: UploadRepositoryImpl
    ): UploadRepository
}