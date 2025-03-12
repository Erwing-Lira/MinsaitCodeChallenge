package com.example.upload.data

import android.net.Uri
import com.example.upload.domain.UploadRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
): UploadRepository {
    companion object {
        const val USER_DIRECTORY = "photos/"
    }
    override suspend fun uploadPhotos(photo: Uri): Result<Unit> {
        return try {
            val uniqueFileName = UUID.randomUUID().toString() + ".jpg"
            val storageRef = firebaseStorage.reference
                .child("$USER_DIRECTORY$uniqueFileName")
            storageRef.putFile(photo).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}