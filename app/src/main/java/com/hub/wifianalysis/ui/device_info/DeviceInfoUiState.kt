package com.hub.wifianalysis.ui.device_info

import com.hub.wifianalysis.model.PortDescription

data class DeviceInfoUiState(
    val ipAddress: String = "",
    val deviceName: String = "",
    val mac: String = "",
    val vendor: String = "",
    val ports: List<PortResultUiState> = emptyList()
)

data class PortResultUiState(
    val port: String,
    val protocol: PortDescription.Protocol,
){
    val protocolEnum get() = if (protocol == PortDescription.Protocol.TCP) PortDescription.Protocol.TCP.name else PortDescription.Protocol.UDP.name
    val serviceName get() =  PortDescription.commonPorts.find { it.port == port.toInt() }?.serviceName ?: ""
}
