package com.hub.wifianalysis.ui.ports_statistics

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.wifianalysis.ui.device_info.PortResultUiState
import com.hub.wifianalysis.ui.home.HomeUiEffect
import com.hub.wifianalysis.ui.home.toUiState
import com.hub.wifianalysis.ui.util.PortScanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tej.androidnetworktools.lib.Device
import tej.androidnetworktools.lib.scanner.OnNetworkScanListener

class PortStatisticViewModel(
   state: SavedStateHandle
): ViewModel(), OnNetworkScanListener {


   private val _state = MutableStateFlow(PortStatisticUiState())
   val state = _state.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<HomeUiEffect>()
    val navigationEvents: SharedFlow<HomeUiEffect> = _navigationEvents
    private val portCountMap = mutableMapOf<String, Int>()
    private var list = mutableListOf<PortResultUiState>()

    @RequiresApi(Build.VERSION_CODES.N)
    private fun fetchInfo(deviceIp:String) {
        viewModelScope.launch(Dispatchers.IO)  {
            Log.e("TAG", "deviceIp: $deviceIp")
            PortScanner(deviceIp).scanPorts().forEach {
                val result = it.await()
                if (result.isOpen) {
                    list.add(PortResultUiState(port = result.port.toString(), protocol = result.protocol))
                    _state.update { it.copy(ports = it.ports + PortResultUiState(port = result.port.toString(), protocol = result.protocol)) }
                    Log.e("TAG", "fetchInfo: ${state.value.ports}" )
                    Log.e("TAG", "list ports: $list")
                }
            }
        }
    }



    fun changeWifiState(isWifiDisabled: Boolean) {
        _state.update { it.copy(isWifiDisabled = isWifiDisabled) }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onComplete(devices: List<Device>) {
        val device = devices.toUiState()
        _state.update { it.copy(isLoading = false, devices = device, isError = false) }
        val allOpenPort = state.value.devices.map {it.ipAddress }
        Log.e("TAG", "allOpenPort: $allOpenPort", )
        _state.update { it.copy(ipsAddress = allOpenPort)}
        allOpenPort.forEach {
            fetchInfo(it)
        }
        Log.e("TAG", "onComplete: ${state.value.devices}")

    }

    override fun onFailed() {
        Log.e("TAG", "onFailed: ")
        _state.update { it.copy(isLoading = false, isError = true) }
    }

}
