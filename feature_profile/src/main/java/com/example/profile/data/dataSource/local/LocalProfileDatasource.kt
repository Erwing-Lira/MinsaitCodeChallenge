package com.example.profile.data.dataSource.local

import com.example.network.data.remote.model.ProfileDTO

interface LocalProfileDatasource {
    suspend fun getProfile(): Result<ProfileDTO>
    suspend fun saveData(profile: ProfileDTO)
}