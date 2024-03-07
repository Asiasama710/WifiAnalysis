package com.hub.wifianalysis.ui.home

import tej.androidnetworktools.lib.Device

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

fun Device.toUiState() = DeviceUiState(
        deviceName = hostname,
        ipAddress = ipAddress,
        macAddress = macAddress,
        vendorName = vendorName,
)

fun List<Device>.toUiState() = map { it.toUiState() }