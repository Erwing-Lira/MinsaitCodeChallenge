package com.example.profile.ui.state

import com.example.network.data.remote.model.ProfileDTO

sealed interface UIState {
    data object Loading : UIState
    data class Success(val profile: ProfileDTO) : UIState
    data class Error(val message: String) : UIState
}