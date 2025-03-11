package com.example.sync

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE
import androidx.core.app.NotificationManagerCompat
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

    companion object {
        private const val CHANNEL_ID = "Main Channel"
        private const val NOTIFICATION_TAG = "SimpleNotification"
        private const val MAIN_NOTIFICATION_ID = 1
    }

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

    private fun showNotification(location: Location) {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Main Channel",
            IMPORTANCE_DEFAULT,
        )
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Ubicación Actualizada")
            .setContentText("Lat: ${location.latitude}, Lng: ${location.longitude}")
            .setSmallIcon(R.drawable.ic_location)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(VISIBILITY_PRIVATE)
            .setPublicVersion(
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("Hidden")
                    .setContentText("Unlock to see the message")
                    .build(),
            )

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(
                NOTIFICATION_TAG,
                MAIN_NOTIFICATION_ID,
                builder.build()
            )
        }
    }
}