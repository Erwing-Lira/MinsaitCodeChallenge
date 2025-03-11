package com.example.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.dao.PopularMovieDao
import com.example.database.dao.TopMovieDao
import com.example.database.entity.PopularMovie
import com.example.database.entity.TopMovies

@Database(
    entities = [PopularMovie::class, TopMovies::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun topMovieDao(): TopMovieDao
}