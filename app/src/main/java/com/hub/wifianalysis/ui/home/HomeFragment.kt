package com.hub.wifianalysis.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hub.wifianalysis.R
import com.hub.wifianalysis.databinding.FragmentHomeBinding
import com.hub.wifianalysis.ui.base.BaseFragment
import com.hub.wifianalysis.ui.conected_devices.DeviceAdapter
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.launch
import tej.androidnetworktools.lib.scanner.NetworkScanner

/**
 * HomeFragment is a class that extends BaseFragment and is used to display the home screen.
 * It uses the layout defined in R.layout.fragment_home for the fragment.
 *
 * @property TAG The tag used for logging.
 * @property layoutIdFragment The layout id for the fragment.
 * @property viewModel The ViewModel used for managing the UI state.
 */
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

    override fun setup() {
        checkWifiState()
    }

    /**
     * Check the wifi state and perform necessary actions based on the state.
     */
    private fun checkWifiState() {
        val wifiManager =
            requireActivity().applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
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
                Log.e("TAG", "wifiManager.connectionInfo.networkId == -1 ")
                viewModel.changeWifiState(true)
                Toast.makeText(
                        context,
                        "Wifi is not connected..Please connect to a wifi network",
                        Toast.LENGTH_LONG
                ).show()
            } else {
                viewModel.changeWifiState(false)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    viewModel.fetchWifiDetails()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkWifiState()
        Log.e("TAG", "onResume ")

    }
    companion object {
        const val REQUEST_CODE_APP_SETTINGS = 1002
    }
}