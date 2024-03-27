package com.hub.wifianalysis.ui.home

import tej.androidnetworktools.lib.Device

/**
 * HomeUiState is a data class that represents the UI state of the Home screen.
 * It includes properties for loading state, error state, wifi state, toast messages, devices, and network info.
 *
 * @property isLoading A Boolean that represents whether the UI is in a loading state.
 * @property isError A Boolean that represents whether there is an error in the UI.
 * @property isWifiDisabled A Boolean that represents whether the wifi is disabled.
 * @property showPleaseWaitToast A Boolean that represents whether to show a "Please Wait" toast message.
 * @property showWifiDisabledToast A Boolean that represents whether to show a "Wifi Disabled" toast message.
 * @property devices A List of DeviceUiState objects that represents the devices in the network.
 * @property networkInfo A NetworkInfoUiState object that represents the network information.
 */
data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isWifiDisabled: Boolean = false,
    val showPleaseWaitToast: Boolean = false,
    val showWifiDisabledToast: Boolean = false,
    val devices: List<DeviceUiState> = emptyList(),
    val networkInfo: NetworkInfoUiState = NetworkInfoUiState(),
)

/**
 * DeviceUiState is a data class that represents the state of a device in the network.
 * It includes properties for IP address, MAC address, device name, and vendor name.
 *
 * @property ipAddress A String that represents the IP address of the device.
 * @property macAddress A String that represents the MAC address of the device.
 * @property deviceName A String that represents the name of the device.
 * @property vendorName A String that represents the vendor name of the device.
 */
data class DeviceUiState(
    val ipAddress: String = "",
    val macAddress: String = "",
    val deviceName: String = "",
    val vendorName: String = "",
)

/**
 * WifiDetailsUiState is a data class that represents the state of the wifi details.
 * It includes properties for public IP, IP address, router IP, DNS, BSSID, SSID, MAC address, link speed, signal strength, frequency, network ID, connection type, and hidden SSID.
 *
 * @property publicIp A String that represents the public IP address.
 * @property ipAddress A String that represents the IP address.
 * @property routerIp A String that represents the router IP address.
 * @property dns1 A String that represents the first DNS.
 * @property dns2 A String that represents the second DNS.
 * @property bssld A String that represents the BSSID.
 * @property ssid A String that represents the SSID.
 * @property macAddress A String that represents the MAC address.
 * @property linkSpeed An Int that represents the link speed.
 * @property signalStrength An Int that represents the signal strength.
 * @property frequency An Int that represents the frequency.
 * @property networkId An Int that represents the network ID.
 * @property connectionType A String that represents the connection type.
 * @property isHiddenSsid A Boolean that represents whether the SSID is hidden.
 */
data class WifiDetailsUiState(
    val publicIp: String = "",
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

/**
 * NetworkInfoUiState is a data class that represents the state of the network information.
 * It includes a property for SSID.
 *
 * @property ssid A String that represents the SSID.
 */
data class NetworkInfoUiState(
    val ssid: String = "",
)

/**
 * Extension function to convert a Device object to a DeviceUiState object.
 *
 * @return A DeviceUiState object that represents the state of the device.
 */
fun Device.toUiState() = DeviceUiState(
    deviceName = hostname ?: "",
    ipAddress = ipAddress?:"",
    macAddress = macAddress?:"",
    vendorName = vendorName ?: "",
)

/**
 * Extension function to convert a List of Device objects to a List of DeviceUiState objects.
 *
 * @return A List of DeviceUiState objects that represents the state of the devices.
 */
fun List<Device>.toUiState() = map { it.toUiState() }