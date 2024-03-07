package com.hub.wifianalysis.ui.home

import com.hub.wifianalysis.R
import com.hub.wifianalysis.model.Device
import com.hub.wifianalysis.ui.base.BaseAdapter

class DeviceAdapter() : BaseAdapter<Device>() {
    override val layoutId: Int = R.layout.item_device
}
