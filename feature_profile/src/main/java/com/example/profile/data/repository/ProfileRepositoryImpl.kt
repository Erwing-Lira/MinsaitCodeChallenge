package com.example.profile.data.repository

import com.example.network.data.remote.model.ProfileDTO
import com.example.network.observe.NetworkObserve
import com.example.profile.data.dataSource.local.LocalProfileDatasource
import com.example.profile.data.dataSource.remote.RemoteProfileDatasource
import com.example.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val networkObserve: NetworkObserve,
    private val remoteProfileDatasource: RemoteProfileDatasource,
    private val localProfileDatasource: LocalProfileDatasource
): ProfileRepository {
    override suspend fun getProfile(): Result<ProfileDTO> {
        val isInternetAvailable = networkObserve.isConnected.value

        return if (isInternetAvailable) {
            val response = remoteProfileDatasource.getProfile()
            response
                .onSuccess { data ->
                    localProfileDatasource.saveData(data)
                    return response
                }
                .onFailure {
                    return localProfileDatasource.getProfile()
                }
        } else {
            localProfileDatasource.getProfile()
        }
    }
}