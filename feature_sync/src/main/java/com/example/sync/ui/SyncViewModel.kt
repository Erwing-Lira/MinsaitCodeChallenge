package com.example.sync.ui

import androidx.lifecycle.ViewModel
import com.example.sync.ui.state.SyncState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(

): ViewModel() {

    private val _permissions = MutableStateFlow(SyncState())
    val permissions: StateFlow<SyncState> = _permissions.asStateFlow()

    fun updateNotificationPermission(isGranted: Boolean) {
        _permissions.update {
            it.copy(
                isNotificationPermissionGranted = isGranted
            )
        }
    }

    fun updateLocationPermission(isGranted: Boolean) {
        _permissions.update {
            it.copy(
                isLocationPermissionGranted = isGranted
            )
        }
    }

}