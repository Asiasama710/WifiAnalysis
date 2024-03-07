package com.hub.wifianalysis.ui.home

import tej.androidnetworktools.lib.Device

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val devices: List<DeviceUiState> = emptyList(),
    val networkInfo: NetworkInfoUiState = NetworkInfoUiState(),
)

data class DeviceUiState(
    val ipAddress: String = "",
    val macAddress: String = "",
    val deviceName: String = "",
    val vendorName: String = "",
)

data class WifiDetailsUiState(
    val ipAddress: String = "",
    val routerIp: String = "",
    val dns1: String = "",
    val dns2: String = "",
    val bssld: String = "",
    val ssid: String = "",
    val macAddress: String = "",
    val linkSpeed: Int = 0,
    val signalStrength: Int = 0,
    val frequency: Int = 0,
    val networkId: Int = 0,
    val connectionType: String = "",
    val isHiddenSsid: Boolean = false
)

data class NetworkInfoUiState(
    val ssid: String = "",
)

fun Device.toUiState() = DeviceUiState(
    deviceName = hostname ?: "",
    ipAddress = ipAddress?:"",
    macAddress = macAddress?:"",
    vendorName = vendorName ?: "",
)


fun List<Device>.toUiState() = map { it.toUiState() }