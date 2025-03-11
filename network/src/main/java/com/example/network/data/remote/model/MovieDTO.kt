package com.example.network.data.remote.model

data class MovieDTO(
    val id: Int,
    val original_title: String,
    val poster_path: String,
    var release_date: String
)
