package com.example.minsaitcodechallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.observe.NetworkObserve
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkObserve: NetworkObserve
): ViewModel() {
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    init {
        viewModelScope.launch {
            networkObserve.isConnected.collect {
                _isConnected.value = it
            }
        }
    }
}