package com.hub.wifianalysis.ui.conected_devices

import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hub.wifianalysis.R

import com.hub.wifianalysis.databinding.FragmentDevicesBinding
import com.hub.wifianalysis.ui.base.BaseFragment
import com.hub.wifianalysis.ui.conected_devices.*
import com.hub.wifianalysis.ui.home.HomeUiEffect
import kotlinx.coroutines.launch
import tej.androidnetworktools.lib.scanner.NetworkScanner

/**
 * DevicesFragment is a class that extends BaseFragment and is used to display a list of connected devices.
 * It uses the layout defined in R.layout.fragment_devices for the fragment.
 */
class DevicesFragment : BaseFragment<FragmentDevicesBinding>() {
    override val TAG: String = this::class.java.simpleName.toString()
    override val layoutIdFragment: Int = R.layout.fragment_devices
    override val viewModel: DevicesViewModel by viewModels()

    /**
     * Setup the fragment. This includes initializing the adapter, checking the wifi state, and setting up the refresh button.
     */
    override fun setup() {
        super.setup()
        initiateAdapter()
        checkWifiState()
        binding.refreshButton.setOnClickListener {
            checkWifiState()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigationEvents.collect { event ->
                when (event) {
                    is HomeUiEffect.NavigateToDetails -> {
                        navigateToDetailsFragment(
                                event.ipAddress,
                                event.macAddress,
                                event.deviceName,
                                event.vendor
                        )
                    }
                }
            }
        }

    }

    /**
     * Navigate to the DetailsFragment with the provided parameters.
     *
     * @param ipAddress The IP address of the device.
     * @param macAddress The MAC address of the device.
     * @param deviceName The name of the device.
     * @param vendor The vendor of the device.
     */
    private fun navigateToDetailsFragment(
        ipAddress: String,
        macAddress: String,
        deviceName: String,
        vendor: String
    ) {
        val action = DevicesFragmentDirections
            .actionDevicesFragmentToDeviceInfoFragment(ipAddress, deviceName, macAddress, vendor)
        findNavController().navigate(action)
    }

    /**
     * Initialize the DeviceAdapter and set it as the adapter for the RecyclerView.
     */
    private fun initiateAdapter() {
        val adapter = DeviceAdapter(viewModel)
        binding.list.adapter = adapter
    }

    /**
     * Check the wifi state. If wifi is not enabled, enable it. If wifi is not connected, prompt the user to connect.
     * If wifi is connected, initiate the adapter and start scanning for devices.
     */
    private fun checkWifiState() {
        val wifiManager =
            requireActivity().applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        Log.e("TAG", "onCreate")
        if (!wifiManager.isWifiEnabled) {
            Toast.makeText(
                    context,
                    "WiFi is disabled ... We need to enable it",
                    Toast.LENGTH_LONG
            ).show()
            wifiManager.isWifiEnabled = true
            viewModel.changeWifiState(true)
        } else {
            if (wifiManager.connectionInfo.networkId == -1) {
                viewModel.changeWifiState(true)
                Toast.makeText(
                        context,
                        "Wifi is not connected..Please connect to a wifi network",
                        Toast.LENGTH_LONG
                ).show()
            } else {
                initiateAdapter()
                viewModel.changeWifiState(false)
                NetworkScanner.init(context)
                if (NetworkScanner.isRunning()) {
                    Toast.makeText(context, "Please wait...", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(context, "Scanning...", Toast.LENGTH_SHORT).show()
                NetworkScanner.scan(viewModel)
            }
        }
    }

}