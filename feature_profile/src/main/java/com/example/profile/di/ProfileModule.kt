package com.example.profile.di

import com.example.profile.data.dataSource.local.LocalProfileDataSourceImpl
import com.example.profile.data.dataSource.local.LocalProfileDatasource
import com.example.profile.data.dataSource.remote.RemoteProfileDataSourceImpl
import com.example.profile.data.dataSource.remote.RemoteProfileDatasource
import com.example.profile.data.repository.ProfileRepositoryImpl
import com.example.profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {
    @Binds
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    abstract fun bindRemoteProfileDataSource(
        remoteProfileDataSourceImpl: RemoteProfileDataSourceImpl
    ): RemoteProfileDatasource

    @Binds
    abstract fun bindLocaleProfileDataSource(
        localProfileDataSourceImpl: LocalProfileDataSourceImpl
    ): LocalProfileDatasource
}