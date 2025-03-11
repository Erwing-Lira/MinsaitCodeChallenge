package com.example.profile.data.dataSource.local

import com.example.database.dao.ProfileDao
import com.example.database.entity.ProfileEntity
import com.example.network.data.remote.model.ProfileDTO
import javax.inject.Inject

class LocalProfileDataSourceImpl @Inject constructor(
    private val profileDao: ProfileDao
): LocalProfileDatasource {
    override suspend fun getProfile(): Result<ProfileDTO> {
        val profileEntity = profileDao.getData()
        return Result.success(
            ProfileDTO(
                id = profileEntity.id,
                avatar_path = profileEntity.avatar_path,
                name = profileEntity.name,
                username = profileEntity.username,
                iso_639_1 = profileEntity.iso_639_1,
                iso_3166_1 = profileEntity.iso_3166_1,
                include_adult = profileEntity.include_adult,
            )
        )
    }

    override suspend fun saveData(profile: ProfileDTO) {
        profileDao.insertData(
            ProfileEntity(
                id = profile.id,
                avatar_path = profile.avatar_path.orEmpty(),
                name = profile.name,
                username = profile.username,
                iso_639_1 = profile.iso_639_1,
                iso_3166_1 = profile.iso_3166_1,
                include_adult = profile.include_adult,
            )
        )
    }
}