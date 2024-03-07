package com.hub.wifianalysis.ui.home

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.hub.wifianalysis.R
import com.hub.wifianalysis.databinding.FragmentHomeBinding
import com.hub.wifianalysis.ui.base.BaseFragment
import tej.androidnetworktools.lib.Device
import tej.androidnetworktools.lib.scanner.NetworkScanner
import tej.androidnetworktools.lib.scanner.OnNetworkScanListener

class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnNetworkScanListener {
    override val TAG: String = this::class.java.simpleName.toString()
    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    override fun setup() {
        NetworkScanner.init(context)
        if (NetworkScanner.isRunning()) {
            Toast.makeText(context, "Please wait...", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(context, "Scanning...", Toast.LENGTH_SHORT).show()
        NetworkScanner.scan(this)
    }

    override fun onComplete(devices: List<Device>) {
        for (device in devices) {
            Log.e(TAG, "ip: ${device.ipAddress}")
            Log.e(TAG, "mac: ${device.macAddress}")
        }

    }

    override fun onFailed() {
        Log.e(TAG, "onFailed: ")
    }
}