package com.hub.wifianalysis.ui.home

import com.hub.wifianalysis.R
import com.hub.wifianalysis.ui.base.BaseAdapter
import com.hub.wifianalysis.ui.base.BaseInteractionListener

class DeviceAdapter(listener: DeviceInteractionListener) : BaseAdapter<DeviceUiState>(listener) {
    override val layoutId: Int = R.layout.item_device
}

interface DeviceInteractionListener : BaseInteractionListener {
    fun onDeviceClick(id: String)
}
