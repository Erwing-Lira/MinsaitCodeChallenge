package com.example.map.domain

import com.example.map.data.GetLocations
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class FetchLocationsUseCase @Inject constructor(
    private val getLocations: GetLocations
) {
    suspend operator fun invoke(): List<CustomLocations>? {
        var result: List<CustomLocations>? = null
        getLocations.getLocationList().fold(
            onSuccess = { locations ->
                result = locations.map { location ->
                    CustomLocations(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        timestamp = Instant.ofEpochMilli(location.timestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                    )
                }
            },
            onFailure = {
                result = null
            }
        )
        return result
    }
}