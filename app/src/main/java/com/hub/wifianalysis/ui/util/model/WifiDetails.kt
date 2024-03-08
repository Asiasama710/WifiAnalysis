package com.hub.wifianalysis.ui.util.model

data class WifiDetails(
    val ipAddress: String,
    val routerIp: String,
    val dns1: String,
    val dns2: String,
    val bssld: String,
    val ssid: String,
    val macAddress: String,
    val linkSpeed: Int,
    val signalStrength: Int,
    val frequency: Int,
    val networkId: Int,
    val connectionType: String,
    val isHiddenSsid: Boolean
)
