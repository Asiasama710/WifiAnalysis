package com.hub.wifianalysis.ui.device_info

import android.telephony.TelephonyCallback.ServiceStateListener
import com.hub.wifianalysis.R
import com.hub.wifianalysis.ui.base.BaseAdapter
import com.hub.wifianalysis.ui.base.BaseInteractionListener
import com.hub.wifianalysis.util.PortScanner

class DeviceInfoAdapter(listener: BaseInteractionListener) : BaseAdapter<PortResultUiState>(listener){
    override val layoutId: Int = R.layout.item_device_info
}