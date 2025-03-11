package com.example.map.di

import com.example.map.data.GetLocations
import com.example.map.data.GetLocationsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapModule {
    @Binds
    abstract fun bindRepository(
        getLocationsImpl: GetLocationsImpl
    ): GetLocations
}