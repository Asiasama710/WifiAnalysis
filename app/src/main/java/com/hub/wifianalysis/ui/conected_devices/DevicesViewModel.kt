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

/**
 * DevicesViewModel is a class that extends ViewModel and implements DeviceInteractionListener and OnNetworkScanListener.
 * It manages the UI state and handles user interactions for the Devices screen.
 *
 * @param state The saved state handle for the ViewModel.
 */
class DevicesViewModel(
   state: SavedStateHandle
): ViewModel(), DeviceInteractionListener, OnNetworkScanListener {

    // The state of the UI.
    private val _state = MutableStateFlow(DevicesUiState())
    val state = _state.asStateFlow()

    // The navigation events.
    private val _navigationEvents = MutableSharedFlow<HomeUiEffect>()
    val navigationEvents: SharedFlow<HomeUiEffect> = _navigationEvents

    /**
     * Handles the click event on a device.
     *
     * @param id The id of the clicked device.
     * @param deviceName The name of the clicked device.
     * @param mac The MAC address of the clicked device.
     * @param vendor The vendor of the clicked device.
     */
    override fun onDeviceClick(id: String, deviceName: String, mac: String, vendor: String) {
        viewModelScope.launch {
            _navigationEvents.emit(HomeUiEffect.NavigateToDetails(ipAddress =id, deviceName = deviceName, macAddress = mac, vendor = vendor))
        }
    }

    /**
     * Changes the wifi state in the UI.
     *
     * @param isWifiDisabled A Boolean that represents whether the wifi is disabled.
     */
    fun changeWifiState(isWifiDisabled: Boolean) {
        _state.update { it.copy(isWifiDisabled = isWifiDisabled) }
    }

    /**
     * Called when the network scan is complete.
     *
     * @param devices The list of devices found in the network scan.
     */
    override fun onComplete(devices: List<Device>) {
        val device = devices.toUiState()
        _state.update { it.copy(isLoading = false, devices = device, isError = false) }
        Log.e("TAG", "onComplete: ${state.value.devices}")
    }

    /**
     * Called when the network scan fails.
     */
    override fun onFailed() {
        Log.e("TAG", "onFailed: ")
        _state.update { it.copy(isLoading = false, isError = true) }
    }

}