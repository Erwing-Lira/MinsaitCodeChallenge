package com.example.upload.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upload.domain.UploadPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadPhotoUseCase: UploadPhotoUseCase
): ViewModel() {
    private val _uri = MutableStateFlow(Uri.EMPTY)
    val uri: StateFlow<Uri> = _uri.asStateFlow()

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error.asStateFlow()

    fun setUri(uri: Uri) {
        _uri.value = uri
    }

    fun uploadPhoto() {
        viewModelScope.launch {
            uploadPhotoUseCase.invoke(_uri.value)
                .onSuccess {
                    _uri.value = Uri.EMPTY
                    _error.value = false
                }
                .onFailure {
                    _uri.value = Uri.EMPTY
                    _error.value = true
                }
        }
    }
}