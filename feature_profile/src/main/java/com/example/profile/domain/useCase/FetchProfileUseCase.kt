package com.example.profile.domain.useCase

import com.example.network.data.remote.model.ProfileDTO
import com.example.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class FetchProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): Result<ProfileDTO> {
        return profileRepository.getProfile()
    }
}