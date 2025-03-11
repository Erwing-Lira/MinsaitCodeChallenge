package com.example.map.data

import com.example.map.data.model.LocationResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetLocationsImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
): GetLocations {
    override suspend fun getLocationList(): Result<List<LocationResponse>> {
        return try {
            val movementSnapshot = firebaseFirestore
                .collection("locations")
                .document("device")
                .get()
                .await()
            if (movementSnapshot.exists()) {
                var movements = emptyList<LocationResponse>()
                if (movementSnapshot.data != null) {
                    val locationList = movementSnapshot.data?.get("locationList") as? List<Map<String, Any>>? ?: emptyList()
                    movements = locationList.mapNotNull { location ->
                        LocationResponse(
                            latitude = location["latitude"] as? Double ?: 0.0,
                            longitude = location["longitude"] as? Double ?: 0.0,
                            timestamp = location["timestamp"] as? Long ?: 0L
                        )
                    }
                } else {
                    null
                }
                Result.success(movements)
            } else {
                Result.failure(NullPointerException())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}