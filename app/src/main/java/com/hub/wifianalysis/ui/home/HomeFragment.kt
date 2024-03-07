package com.hub.wifianalysis.ui.home

import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
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
        viewModel.fetchWifiDetails()
        NetworkScanner.init(context)
        if (NetworkScanner.isRunning()) {
            Toast.makeText(context, "Please wait...", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(context, "Scanning...", Toast.LENGTH_SHORT).show()
        NetworkScanner.scan(viewModel)
        initiateAdapter()
    }

    private fun initiateAdapter() {
        val adapter = DeviceAdapter()
        binding.list.adapter = adapter
    }
}