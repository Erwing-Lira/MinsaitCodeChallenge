package com.example.map.domain

import java.time.LocalDateTime

data class CustomLocations(
    val latitude: Double,
    val longitude: Double,
    val timestamp: LocalDateTime
)
