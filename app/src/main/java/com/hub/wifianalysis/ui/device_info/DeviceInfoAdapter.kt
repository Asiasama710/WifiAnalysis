package com.hub.wifianalysis.ui.device_info

import com.hub.wifianalysis.R
import com.hub.wifianalysis.ui.base.BaseAdapter
import com.hub.wifianalysis.ui.base.BaseInteractionListener

class DeviceInfoAdapter(listener: BaseInteractionListener) : BaseAdapter<PortResultUiState>(listener){
    override val layoutId: Int = R.layout.item_device_info
}