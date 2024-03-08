package com.hub.wifianalysis.ui.home

import android.Manifest
import android.content.Intent
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
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.launch
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

    override fun setup() {
        setupPermissionRequest()
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


    private fun setupPermissionRequest() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "Core fundamental are based on these permissions",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    checkWifiState()
                } else {
                    showPermissionDeniedDialog()
                    Toast.makeText(
                        requireContext(),
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }


    private fun showPermissionDeniedDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Permission Required")
        builder.setMessage("Location permission is required to proceed. Please enable it in Settings.")
        builder.setPositiveButton("Go to Settings") { dialog, _ ->
            dialog.dismiss()
            openLocationSettings()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun openLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivityForResult(intent, REQUEST_CODE_APP_SETTINGS)
    }

    private fun initiateAdapter() {
        val adapter = DeviceAdapter(viewModel)
        binding.list.adapter = adapter
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
                initiateAdapter()
                viewModel.changeWifiState(false)
                NetworkScanner.init(context)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    viewModel.fetchWifiDetails()
                }
                if (NetworkScanner.isRunning()) {
                    Toast.makeText(context, "Please wait...", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(context, "Scanning...", Toast.LENGTH_SHORT).show()
                NetworkScanner.scan(viewModel)
            }
        }
    }

    private fun navigateToDetailsFragment(
        ipAddress: String,
        macAddress: String,
        deviceName: String,
        vendor: String
    ) {
        val action = HomeFragmentDirections
            .actionHomeFragmentToDeviceInfoFragment(ipAddress, deviceName, macAddress, vendor)
        findNavController().navigate(action)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        private const val REQUEST_CODE_APP_SETTINGS = 1002
    }
}