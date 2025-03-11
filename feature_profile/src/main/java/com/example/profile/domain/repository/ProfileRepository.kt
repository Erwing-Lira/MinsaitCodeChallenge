package com.example.profile.domain.repository

import com.example.network.data.remote.model.ProfileDTO

interface ProfileRepository {
    suspend fun getProfile(): Result<ProfileDTO>
}