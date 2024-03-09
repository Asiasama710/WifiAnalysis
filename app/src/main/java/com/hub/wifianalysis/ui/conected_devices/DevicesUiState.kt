package com.hub.wifianalysis.ui.conected_devices

import com.hub.wifianalysis.ui.home.DeviceUiState

data class DevicesUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isWifiDisabled: Boolean = false,
    val devices: List<DeviceUiState> = emptyList(),
)