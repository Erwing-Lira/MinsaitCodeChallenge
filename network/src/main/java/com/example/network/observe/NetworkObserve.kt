package com.example.network.observe

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkObserve @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    init {
        observeConnection()
    }

    private fun observeConnection() {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                _isConnected.update { true }
            }

            override fun onLost(network: Network) {
                _isConnected.update { false }
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            networkCallback
        )
    }
}