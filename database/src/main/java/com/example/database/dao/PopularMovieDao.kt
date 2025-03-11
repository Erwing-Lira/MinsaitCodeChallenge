package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entity.PopularMovie

@Dao
interface PopularMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovies(movies: List<PopularMovie>)

    @Query("SELECT * FROM popular_movies")
    suspend fun getAllPopularMovies(): List<PopularMovie>

    @Query("DELETE FROM popular_movies")
    suspend fun deleteAll()
}