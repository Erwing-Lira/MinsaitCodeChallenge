package com.example.map.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.map.domain.FetchLocationsUseCase
import com.example.map.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val fetchLocationsUseCase: FetchLocationsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun fetchLocations() {
        viewModelScope.launch {
            val res = fetchLocationsUseCase.invoke()
            res?.let {
                _uiState.update {
                    it.copy(
                        locations = res,
                        isLoading = false
                    )
                }
            } ?: run {
                _uiState.update {
                    it.copy(
                        error = "Something went wrong",
                        isLoading = false
                    )
                }
            }
        }
    }
}