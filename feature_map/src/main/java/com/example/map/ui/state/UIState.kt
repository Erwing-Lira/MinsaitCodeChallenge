package com.example.map.ui.state

import com.example.map.domain.CustomLocations

data class UIState(
    val locations: List<CustomLocations> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
