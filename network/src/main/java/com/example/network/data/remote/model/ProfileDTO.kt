package com.example.network.data.remote.model

data class ProfileDTO(
    val id: Int,
    val avatar_path: String? = "",
    val iso_639_1: String = "",
    val iso_3166_1: String = "",
    val name: String = "",
    val include_adult: Boolean = false,
    val username: String = ""
)
