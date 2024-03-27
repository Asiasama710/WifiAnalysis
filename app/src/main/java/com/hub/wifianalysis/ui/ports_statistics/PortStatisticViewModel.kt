package com.hub.wifianalysis.ui.ports_statistics

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.wifianalysis.ui.device_info.PortResultUiState
import com.hub.wifianalysis.ui.home.HomeUiEffect
import com.hub.wifianalysis.ui.home.toUiState
import com.hub.wifianalysis.ui.util.PortScanner
import com.hub.wifianalysis.ui.util.model.PortDescription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tej.androidnetworktools.lib.Device
import tej.androidnetworktools.lib.scanner.OnNetworkScanListener

/**
 * PortStatisticViewModel is a class that extends ViewModel and is used to manage the UI state for the Port Statistics screen.
 * It includes properties for the state of the UI and implements the OnNetworkScanListener interface.
 *
 * @property _state A MutableStateFlow that represents the state of the UI.
 * @property state A StateFlow that represents the state of the UI.
 */
class PortStatisticViewModel(): ViewModel(), OnNetworkScanListener {

   private val _state = MutableStateFlow(PortStatisticUiState())
   val state = _state.asStateFlow()

    /**
     * Fetches the information about the device with the given IP address.
     *
     * @param deviceIp A String that represents the IP address of the device.
     */
    @RequiresApi(Build.VERSION_CODES.N)
    private suspend fun fetchInfo(deviceIp:String) {
        withContext(Dispatchers.IO)  {
            PortScanner(deviceIp).scanPorts().forEach {portScan ->
                val result = portScan.await()
                if (result.isOpen) {
                    Log.e("TAG", "deviceIp: $deviceIp")
                    _state.update{ it.copy(ports = it.ports.toMutableList().apply {
                                    add(PortResultUiState(port = result.port.toString(), protocol = result.protocol))
                                }
                        )
                    }
                    val newPortConteValue =state.value.portCountMap.toMutableMap().apply {
                        val serviceName = PortDescription.commonPorts.find { p -> p.port == result.port }?.serviceName ?: ""
                        val count = getOrDefault(Pair(result.port.toString(),serviceName), 0)
                        put(Pair(result.port.toString(),serviceName), count + 1) }.toList().distinctBy { it.first.second }?.toMap()
                       _state.update { it.copy(portCountMap = newPortConteValue ?: emptyMap()) }
                }
            }
        }
    }

    /**
     * Changes the Wi-Fi state and updates the UI state.
     *
     * @param isWifiDisabled A Boolean that represents whether the Wi-Fi is disabled.
     */
    fun changeWifiState(isWifiDisabled: Boolean) {
        _state.update { it.copy(isWifiDisabled = isWifiDisabled) }
    }

    /**
     * Called when the network scan is complete. It updates the UI state with the list of devices and fetches the information about each device.
     *
     * @param devices A List of Device objects that represents the devices in the network.
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onComplete(devices: List<Device>) {
        val device = devices.toUiState()
        _state.update { it.copy(isLoading = false, devices = device, isError = false) }
        try{
            viewModelScope.launch {

                val allOpenPort = state.value.devices.map { it.ipAddress }
                Log.e("TAG", "allOpenPort: $allOpenPort")
                async { allOpenPort.forEach { fetchInfo(it) } }.await()
                _state.update { it.copy(isSuccessful = true)}
            }
        }catch (e:Exception){
            Log.e("TAG", "onComplete: ${e.message}")
        }

    }

    /**
     * Called when the network scan fails. It updates the UI state to show an error.
     */
    override fun onFailed() {
        Log.e("TAG", "onFailed: ")
        _state.update { it.copy(isLoading = false, isError = true) }
    }
}