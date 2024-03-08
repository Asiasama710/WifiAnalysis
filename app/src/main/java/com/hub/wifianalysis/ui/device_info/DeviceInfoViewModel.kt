package com.hub.wifianalysis.ui.device_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DeviceInfoViewModel(
   state: SavedStateHandle
): ViewModel() {

   private val deviceArgs = DeviceInfoFragmentArgs.fromSavedStateHandle(state)

   private val _state = MutableStateFlow(DeviceInfoUiState())
   val state = _state.asStateFlow()

   init {
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



}
