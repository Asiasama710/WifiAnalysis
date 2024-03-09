package com.hub.wifianalysis.ui.ports_statistics

import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.hub.wifianalysis.R
import com.hub.wifianalysis.databinding.FragmentPortsStatisticsBinding
import com.hub.wifianalysis.ui.base.BaseFragment
import tej.androidnetworktools.lib.scanner.NetworkScanner


class PortsStatisticsFragment : BaseFragment<FragmentPortsStatisticsBinding>() {
    override val TAG: String = this::class.java.simpleName.toString()
    override val layoutIdFragment: Int = R.layout.fragment_ports_statistics
    override val viewModel: PortStatisticViewModel by viewModels()

    override fun setup() {
        super.setup()
        checkWifiState()
//        binding.refreshButton.setOnClickListener {
//            checkWifiState()
//        }
    }


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