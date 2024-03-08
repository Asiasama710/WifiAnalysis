package com.hub.wifianalysis.ui.home

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hub.wifianalysis.ui.util.model.WifiDetails
import com.hub.wifianalysis.ui.util.WifiUtils
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tej.androidnetworktools.lib.Device
import tej.androidnetworktools.lib.scanner.OnNetworkScanListener

class HomeViewModel(application: Application) : AndroidViewModel(application),
    OnNetworkScanListener, DeviceInteractionListener {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    // Mutable state flow for Wi-Fi details UI state
    private val _wifiDetailsUiState = MutableStateFlow(WifiDetailsUiState())
    val wifiDetailsUiState = _wifiDetailsUiState.asStateFlow()
    private val appContext = getApplication<Application>().applicationContext
    private val _navigationEvents = MutableSharedFlow<HomeUiEffect>()
    val navigationEvents: SharedFlow<HomeUiEffect> = _navigationEvents

    init {
        _state.update { it.copy(isLoading = true) }
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun fetchWifiDetails() {
        viewModelScope.launch {
            WifiUtils.getWifiDetails(appContext) { wifiDetails ->
                if (wifiDetails != null) {
                    updateWifiDetailsUiState(wifiDetails)
                } else {
                    // Handle error
                    Log.e("TAG", "Error fetching Wi-Fi details")
                }
            }
        }
    }

    fun changeWifiState(isWifiDisabled: Boolean) {
        _state.update { it.copy(isWifiDisabled = isWifiDisabled) }
    }

    private fun updateWifiDetailsUiState(wifiDetails: WifiDetails) {
        _wifiDetailsUiState.value = WifiDetailsUiState(
            ipAddress = wifiDetails.ipAddress,
            routerIp = wifiDetails.routerIp,
            dns1 = wifiDetails.dns1,
            dns2 = wifiDetails.dns2,
            ssid = wifiDetails.ssid,
            macAddress = wifiDetails.macAddress,
            linkSpeed = wifiDetails.linkSpeed,
            signalStrength = wifiDetails.signalStrength,
            frequency = wifiDetails.frequency,
            networkId = wifiDetails.networkId,
            connectionType = wifiDetails.connectionType,
            isHiddenSsid = wifiDetails.isHiddenSsid
        )
    }

    override fun onDeviceClick(id: String, deviceName: String, mac: String, vendor: String) {
        viewModelScope.launch {
            _navigationEvents.emit(HomeUiEffect.NavigateToDetails(ipAddress =id, deviceName = deviceName, macAddress = mac, vendor = vendor))
        }
    }
}