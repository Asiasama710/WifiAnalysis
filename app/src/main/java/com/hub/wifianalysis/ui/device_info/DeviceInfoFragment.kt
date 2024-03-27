package com.hub.wifianalysis.ui.device_info

import androidx.fragment.app.viewModels
import com.hub.wifianalysis.R
import com.hub.wifianalysis.databinding.FragmentDeviceInfoBinding
import com.hub.wifianalysis.ui.base.BaseFragment

/**
 * DeviceInfoFragment is a class that extends BaseFragment and is used to display device information.
 * It uses the layout defined in R.layout.fragment_device_info for the fragment.
 *
 * @property TAG The tag used for logging.
 * @property layoutIdFragment The layout id for the fragment.
 * @property viewModel The ViewModel used for managing the UI state.
 */
class DeviceInfoFragment : BaseFragment<FragmentDeviceInfoBinding>() {
    override val TAG: String = this::class.java.simpleName.toString()
    override val layoutIdFragment: Int = R.layout.fragment_device_info
    override val viewModel: DeviceInfoViewModel by viewModels()

    /**
     * Setup the fragment. This includes initializing the adapter.
     */
    override fun setup() {
        super.setup()
        initiateAdapter()
    }

    /**
     * Initialize the DeviceInfoAdapter and set it as the adapter for the RecyclerView.
     */
    private fun initiateAdapter() {
        val adapter = DeviceInfoAdapter(viewModel)
        binding.list.adapter = adapter
    }
}