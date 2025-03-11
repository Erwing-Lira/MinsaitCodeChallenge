package com.example.profile.data.dataSource.remote

import com.example.network.data.remote.model.ProfileDTO

interface RemoteProfileDatasource {
    suspend fun getProfile(): Result<ProfileDTO>
}