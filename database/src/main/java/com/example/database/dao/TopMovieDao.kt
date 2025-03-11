package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entity.TopMovies

@Dao
interface TopMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopMovies(movies: List<TopMovies>)

    @Query("SELECT * FROM top_movies")
    suspend fun getAllTopMovies(): List<TopMovies>

    @Query("DELETE FROM top_movies")
    suspend fun deleteAll()
}