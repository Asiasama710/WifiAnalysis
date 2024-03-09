package com.hub.wifianalysis.ui.conected_devices

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.wifianalysis.ui.base.BaseInteractionListener
import com.hub.wifianalysis.ui.home.HomeUiEffect
import com.hub.wifianalysis.ui.home.toUiState
import com.hub.wifianalysis.ui.util.PortScanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tej.androidnetworktools.lib.Device
import tej.androidnetworktools.lib.scanner.OnNetworkScanListener

class DevicesViewModel(
   state: SavedStateHandle
): ViewModel(), DeviceInteractionListener, OnNetworkScanListener {


   private val _state = MutableStateFlow(DevicesUiState())
   val state = _state.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<HomeUiEffect>()
    val navigationEvents: SharedFlow<HomeUiEffect> = _navigationEvents

    override fun onDeviceClick(id: String, deviceName: String, mac: String, vendor: String) {
        viewModelScope.launch {
            _navigationEvents.emit(HomeUiEffect.NavigateToDetails(ipAddress =id, deviceName = deviceName, macAddress = mac, vendor = vendor))
        }
    }

    fun changeWifiState(isWifiDisabled: Boolean) {
        _state.update { it.copy(isWifiDisabled = isWifiDisabled) }
    }

    override fun onComplete(devices: List<Device>) {
        val device = devices.toUiState()
        _state.update { it.copy(isLoading = false, devices = device, isError = false) }
        Log.e("TAG", "onComplete: ${state.value.devices}")
    }

    override fun onFailed() {
        Log.e("TAG", "onFailed: ")
        _state.update { it.copy(isLoading = false, isError = true) }
    }

}
