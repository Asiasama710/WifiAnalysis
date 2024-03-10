package com.hub.wifianalysis.ui.ports_statistics

import com.hub.wifianalysis.ui.device_info.PortResultUiState
import com.hub.wifianalysis.ui.home.DeviceUiState

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