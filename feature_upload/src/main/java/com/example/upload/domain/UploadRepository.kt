package com.example.upload.domain

import android.net.Uri

interface UploadRepository {
    suspend fun uploadPhotos(photo: Uri): Result<Unit>
}