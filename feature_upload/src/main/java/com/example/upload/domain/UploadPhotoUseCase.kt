package com.example.upload.domain

import android.net.Uri
import javax.inject.Inject

class UploadPhotoUseCase @Inject constructor(
    private val uploadRepository: UploadRepository
) {
    suspend operator fun invoke(photo: Uri): Result<Unit> {
        return uploadRepository.uploadPhotos(photo)
    }
}