package com.example.map.data

import com.example.map.data.model.LocationResponse

interface GetLocations {
    suspend fun getLocationList(): Result<List<LocationResponse>>
}