package com.hub.wifianalysis.ui.conected_devices

import com.hub.wifianalysis.ui.home.DeviceUiState

/**
 * DevicesUiState is a data class that represents the UI state of the Devices screen.
 * It includes properties for loading state, error state, wifi disabled state, and a list of devices.
 *
 * @property isLoading A Boolean that represents whether the data is currently being loaded.
 * @property isError A Boolean that represents whether there was an error loading the data.
 * @property isWifiDisabled A Boolean that represents whether the wifi is disabled.
 * @property devices A List of DeviceUiState objects that represents the devices connected to the wifi.
 */
data class DevicesUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isWifiDisabled: Boolean = false,
    val devices: List<DeviceUiState> = emptyList(),
)