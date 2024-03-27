package com.hub.wifianalysis.ui.conected_devices

import com.hub.wifianalysis.R
import com.hub.wifianalysis.ui.base.BaseAdapter
import com.hub.wifianalysis.ui.base.BaseInteractionListener
import com.hub.wifianalysis.ui.home.DeviceUiState

/**
 * DeviceAdapter is a class that extends BaseAdapter and is used to display a list of DeviceUiState objects.
 * It uses the layout defined in R.layout.item_device for each item in the list.
 *
 * @param listener The listener that receives interaction events.
 */
class DeviceAdapter(listener: DeviceInteractionListener) : BaseAdapter<DeviceUiState>(listener) {
    override val layoutId: Int = R.layout.item_device
}

/**
 * DeviceInteractionListener is an interface that extends BaseInteractionListener.
 * It defines a method for handling click events on a device.
 */
interface DeviceInteractionListener : BaseInteractionListener {
    /**
     * Called when a device is clicked.
     *
     * @param id The id of the clicked device.
     * @param deviceName The name of the clicked device.
     * @param mac The MAC address of the clicked device.
     * @param vendor The vendor of the clicked device.
     */
    fun onDeviceClick(id: String,deviceName:String,mac:String,vendor:String)
}