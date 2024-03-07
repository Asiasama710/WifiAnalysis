package com.hub.wifianalysis.ui.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val devices: List<DeviceUiState> = emptyList(),
    val networkInfo: NetworkInfoUiState = NetworkInfoUiState()
)

data class DeviceUiState(
    val ipAddress: String = "",
    val macAddress: String = "",
    val deviceName: String = "",
    val vendorName: String = "",
)

data class NetworkInfoUiState(
    val ssid: String = "",
)