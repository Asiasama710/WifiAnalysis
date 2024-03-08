package com.hub.wifianalysis.ui.device_info

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.wifianalysis.ui.base.BaseInteractionListener
import com.hub.wifianalysis.ui.util.PortScanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeviceInfoViewModel(
   state: SavedStateHandle
): ViewModel(), BaseInteractionListener {

   private val deviceArgs = DeviceInfoFragmentArgs.fromSavedStateHandle(state)

   private val _state = MutableStateFlow(DeviceInfoUiState())
   val state = _state.asStateFlow()

   init {
       fetchInfo()
      getDeviceInfo()
   }
   private fun getDeviceInfo() {
       _state.value = DeviceInfoUiState(
           ipAddress = deviceArgs.deviceip,
           deviceName = deviceArgs.deviceName,
           mac = deviceArgs.deviceMac,
           vendor = deviceArgs.deviceVendor
       )
   }

   private fun fetchInfo() {
      viewModelScope.launch(Dispatchers.IO)  {
         PortScanner(deviceArgs.deviceip).scanPorts().forEach {
                  val result = it.await()
                  if (result.isOpen) {
                      Log.e("TAG", "fetchInfo: $result")
                     _state.update { it.copy(ports = it.ports + PortResultUiState(port = result.port.toString(), protocol = result.protocol)) }
                      Log.e("TAG", "fetchInfo: ${state.value.ports}" )
                  }
            }
      }
   }



}
