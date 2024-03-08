package com.hub.wifianalysis.ui.home

import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hub.wifianalysis.R
import com.hub.wifianalysis.databinding.FragmentHomeBinding
import com.hub.wifianalysis.ui.base.BaseFragment
import tej.androidnetworktools.lib.scanner.NetworkScanner

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val TAG: String = this::class.java.simpleName.toString()
    override val layoutIdFragment: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(requireActivity().application) as T
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun setup() {
        val wifiManager = requireActivity().applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        checkWifiState(wifiManager)
        binding.refreshButton.setOnClickListener {
            checkWifiState(wifiManager)
        }
    }

    private fun initiateAdapter() {
        val adapter = DeviceAdapter()
        binding.list.adapter = adapter
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun checkWifiState(wifiManager: WifiManager) {
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
                viewModel.fetchWifiDetails()
                if (NetworkScanner.isRunning()) {
                    Toast.makeText(context, "Please wait...", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(context, "Scanning...", Toast.LENGTH_SHORT).show()
                NetworkScanner.scan(viewModel)
            }
        }
    }
}