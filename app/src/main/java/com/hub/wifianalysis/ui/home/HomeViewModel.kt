package com.hub.wifianalysis.ui.home

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hub.wifianalysis.ui.conected_devices.DeviceInteractionListener
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

/**
 * HomeViewModel is a class that extends AndroidViewModel and is used to manage the UI state for the Home screen.
 * It includes properties for the application context, state of the UI, and Wi-Fi details UI state.
 *
 * @property appContext The application context.
 * @property _state A MutableStateFlow that represents the state of the UI.
 * @property state A StateFlow that represents the state of the UI.
 * @property _wifiDetailsUiState A MutableStateFlow that represents the Wi-Fi details UI state.
 * @property wifiDetailsUiState A StateFlow that represents the Wi-Fi details UI state.
 */
class HomeViewModel(application: Application) : AndroidViewModel(application){

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    // Mutable state flow for Wi-Fi details UI state
    private val _wifiDetailsUiState = MutableStateFlow(WifiDetailsUiState())
    val wifiDetailsUiState = _wifiDetailsUiState.asStateFlow()
    private val appContext = getApplication<Application>().applicationContext

    init {
        _state.update { it.copy(isLoading = true) }
    }

    /**
     * Fetches the Wi-Fi details and updates the Wi-Fi details UI state.
     */
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

    /**
     * Changes the Wi-Fi state and updates the UI state.
     *
     * @param isWifiDisabled A Boolean that represents whether the Wi-Fi is disabled.
     */
    fun changeWifiState(isWifiDisabled: Boolean) {
        _state.update { it.copy(isWifiDisabled = isWifiDisabled) }
    }

    /**
     * Updates the Wi-Fi details UI state with the given Wi-Fi details.
     *
     * @param wifiDetails A WifiDetails object that represents the Wi-Fi details.
     */
    private fun updateWifiDetailsUiState(wifiDetails: WifiDetails) {
        _wifiDetailsUiState.value = WifiDetailsUiState(
            publicIp = wifiDetails.publicIp,
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
}