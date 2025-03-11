package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_movies")
data class TopMovies(
    @PrimaryKey val id: Int,
    val original_title: String,
    val poster_path: String,
    val release_date: String
)