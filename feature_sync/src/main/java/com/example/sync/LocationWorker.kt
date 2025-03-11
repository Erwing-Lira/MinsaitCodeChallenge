package com.example.sync

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LocationWorker (
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    private val focusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun doWork(): Result {
        getCurrentLocation()
        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        focusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                Log.e("LocationWorker", "location: $location")
                if (location != null) {
                    sendLocationToFirebase(location)
                    showNotification(location)
                }
            }
            .addOnFailureListener {
                Log.e("LocationWorker", "Error obteniendo la ubicación")
            }
    }

    private fun sendLocationToFirebase(location: Location) {
        val db = Firebase.firestore
        val locationData = mapOf(
            "latitude" to location.latitude,
            "longitude" to location.longitude,
            "timestamp" to System.currentTimeMillis()
        )

        val ref = db.collection("locations").document("device")
        ref.update(
            "locationList", FieldValue.arrayUnion(locationData)
        )
            .addOnSuccessListener {
                Log.d("LocationWorker", "Ubicación enviada a Firebase")
            }
            .addOnFailureListener {
                Log.e("LocationWorker", "Error al enviar la ubicación")
            }
    }

    @SuppressLint("ServiceCast")
    private fun showNotification(location: Location) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "location_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Location Updates", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Ubicación Actualizada")
            .setContentText("Lat: ${location.latitude}, Lng: ${location.longitude}")
            .setSmallIcon(R.drawable.ic_location)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)
    }
}