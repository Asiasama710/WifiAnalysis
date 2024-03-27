package com.hub.wifianalysis.ui.device_info

import com.hub.wifianalysis.R
import com.hub.wifianalysis.ui.base.BaseAdapter
import com.hub.wifianalysis.ui.base.BaseInteractionListener

/**
 * DeviceInfoAdapter is a class that extends BaseAdapter and is used to display a list of PortResultUiState objects.
 * It uses the layout defined in R.layout.item_device_info for each item in the list.
 *
 * @param listener The listener that receives interaction events.
 */
class DeviceInfoAdapter(listener: BaseInteractionListener) : BaseAdapter<PortResultUiState>(listener){
    override val layoutId: Int = R.layout.item_device_info
}