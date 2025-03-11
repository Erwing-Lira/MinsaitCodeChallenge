package com.example.profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profile.domain.useCase.FetchProfileUseCase
import com.example.profile.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchProfileUseCase: FetchProfileUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            fetchProfileUseCase.invoke()
                .onSuccess { data ->
                    _uiState.update {
                        UIState.Success(data)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        UIState.Error(error.message ?: "Unknown error")
                    }
                }

        }
    }
}