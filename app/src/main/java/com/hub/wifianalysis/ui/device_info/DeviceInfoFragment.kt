package com.hub.wifianalysis.ui.device_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hub.wifianalysis.R
import com.hub.wifianalysis.databinding.FragmentDeviceInfoBinding
import com.hub.wifianalysis.ui.base.BaseFragment


class DeviceInfoFragment : BaseFragment<FragmentDeviceInfoBinding>() {
    override val TAG: String = this::class.java.simpleName.toString()
    override val layoutIdFragment: Int = R.layout.fragment_device_info
    override val viewModel: DeviceInfoViewModel by viewModels()

    override fun setup() {
        super.setup()

    }

}