package com.hub.wifianalysis.ui.ports_statistics

import com.hub.wifianalysis.ui.device_info.PortResultUiState
import com.hub.wifianalysis.ui.home.DeviceUiState

/**
 * PortStatisticUiState is a data class that represents the UI state of the Port Statistics screen.
 * It includes properties for loading state, error state, wifi state, devices, ports, success state, list of port results, and a map of port counts.
 *
 * @property isLoading A Boolean that represents whether the UI is in a loading state.
 * @property isError A Boolean that represents whether there is an error in the UI.
 * @property isWifiDisabled A Boolean that represents whether the wifi is disabled.
 * @property devices A List of DeviceUiState objects that represents the devices in the network.
 * @property ports A List of PortResultUiState objects that represents the port results.
 * @property isSuccessful A Boolean that represents whether the operation was successful.
 * @property list A List of PortResultUiState objects that represents the list of port results.
 * @property portCountMap A Map of Pair of Strings to Int that represents the count of each port.
 */
data class PortStatisticUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isWifiDisabled: Boolean = false,
    val devices: List<DeviceUiState> = emptyList(),
    val ports: List<PortResultUiState> = emptyList(),
    val isSuccessful: Boolean = false,
    val list: List<PortResultUiState> = emptyList(),
    val portCountMap: Map<Pair<String,String>, Int> = mutableMapOf(),
)