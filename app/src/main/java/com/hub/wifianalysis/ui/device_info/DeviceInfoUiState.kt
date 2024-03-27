package com.hub.wifianalysis.ui.device_info

import com.hub.wifianalysis.ui.util.model.PortDescription

/**
 * DeviceInfoUiState is a data class that represents the UI state of the DeviceInfo screen.
 * It includes properties for IP address, device name, MAC address, vendor, and a list of ports.
 *
 * @property ipAddress A String that represents the IP address of the device.
 * @property deviceName A String that represents the name of the device.
 * @property mac A String that represents the MAC address of the device.
 * @property vendor A String that represents the vendor of the device.
 * @property ports A List of PortResultUiState objects that represents the ports of the device.
 */
data class DeviceInfoUiState(
    val ipAddress: String = "",
    val deviceName: String = "",
    val mac: String = "",
    val vendor: String = "",
    val ports: List<PortResultUiState> = emptyList()
)

/**
 * PortResultUiState is a data class that represents the state of a port result.
 * It includes properties for port, protocol, and computed properties for protocolEnum and serviceName.
 *
 * @property port A String that represents the port number.
 * @property protocol A Protocol object that represents the protocol of the port.
 * @property protocolEnum A computed property that returns the name of the protocol.
 * @property serviceName A computed property that returns the service name of the port.
 */
data class PortResultUiState(
    val port: String,
    val protocol: PortDescription.Protocol,
){
    val protocolEnum get() = if (protocol == PortDescription.Protocol.TCP) PortDescription.Protocol.TCP.name else PortDescription.Protocol.UDP.name
    val serviceName get() =  PortDescription.commonPorts.find { it.port == port.toInt() }?.serviceName ?: ""
}