package com.hub.wifianalysis.ui.util.model

/**
 * Data class representing the details of a Wi-Fi connection.
 *
 * @property publicIp The public IP address of the device.
 * @property ipAddress The IP address of the device on the local network.
 * @property routerIp The IP address of the router.
 * @property dns1 The IP address of the primary DNS server.
 * @property dns2 The IP address of the secondary DNS server.
 * @property bssld The basic service set identifier (BSSID) of the Wi-Fi network.
 * @property ssid The service set identifier (SSID) of the Wi-Fi network.
 * @property macAddress The MAC address of the device.
 * @property linkSpeed The link speed of the Wi-Fi connection in Mbps.
 * @property signalStrength The signal strength of the Wi-Fi connection in dBm.
 * @property frequency The frequency of the Wi-Fi connection in MHz.
 * @property networkId The network ID of the Wi-Fi connection.
 * @property connectionType The type of the network connection (e.g., Wi-Fi, mobile).
 * @property isHiddenSsid A Boolean indicating whether the SSID of the Wi-Fi network is hidden.
 */
data class WifiDetails(
    val publicIp: String,
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