package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
class ProfileEntity(
    @PrimaryKey
    val id: Int,
    val avatar_path: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val include_adult: Boolean,
    val username: String
)