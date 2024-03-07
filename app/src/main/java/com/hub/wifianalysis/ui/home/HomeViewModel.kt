package com.hub.wifianalysis.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hub.wifianalysis.ui.base.BaseInteractionListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tej.androidnetworktools.lib.Device
import tej.androidnetworktools.lib.scanner.OnNetworkScanListener

class HomeViewModel() : ViewModel(), OnNetworkScanListener {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    override fun onComplete(devices: List<Device>) {
        val device = devices.toUiState()
        _state.update { it.copy(isLoading = false, devices = device) }
        Log.e("TAG", "onComplete: ${state.value.devices}")
    }

    override fun onFailed() {
        Log.e("TAG", "onFailed: ")
    }
}