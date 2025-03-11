package com.example.network.data.remote.api

import com.example.network.BuildConfig
import com.example.network.data.remote.model.ProfileDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {
    @GET("account/{account_id}")
    suspend fun getProfile(
        @Path("account_id") accountId: String = BuildConfig.ACCOUNT_ID,
    ): Response<ProfileDTO>
}