package com.example.profile.data.dataSource.remote

import com.example.network.data.remote.api.ProfileService
import com.example.network.data.remote.model.ProfileDTO
import javax.inject.Inject

class RemoteProfileDataSourceImpl @Inject constructor(
    private val profileService: ProfileService
): RemoteProfileDatasource {
    override suspend fun getProfile(): Result<ProfileDTO> {
        return try {
            val response = profileService.getProfile()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Throwable("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}