package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movies")
data class PopularMovie(
    @PrimaryKey val id: Int,
    val original_title: String,
    val poster_path: String,
    var release_date: String
)