package com.hub.wifianalysis.ui.conected_devices

import com.hub.wifianalysis.R
import com.hub.wifianalysis.ui.base.BaseAdapter
import com.hub.wifianalysis.ui.base.BaseInteractionListener
import com.hub.wifianalysis.ui.home.DeviceUiState

class DeviceAdapter(listener: DeviceInteractionListener) : BaseAdapter<DeviceUiState>(listener) {
    override val layoutId: Int = R.layout.item_device
}

interface DeviceInteractionListener : BaseInteractionListener {
    fun onDeviceClick(id: String,deviceName:String,mac:String,vendor:String)
}
