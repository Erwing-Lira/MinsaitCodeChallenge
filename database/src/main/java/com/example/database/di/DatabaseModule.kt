package com.example.database.di

import android.app.Application
import androidx.room.Room
import com.example.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePopularMovieDao(database: AppDatabase) = database.popularMovieDao()

    @Provides
    @Singleton
    fun provideTopMovieDao(database: AppDatabase) = database.topMovieDao()
}