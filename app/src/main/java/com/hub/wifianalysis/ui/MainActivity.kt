package com.hub.wifianalysis.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.hub.wifianalysis.R
import com.hub.wifianalysis.databinding.ActivityMainBinding
import tej.androidnetworktools.lib.Device
import tej.androidnetworktools.lib.scanner.NetworkScanner
import tej.androidnetworktools.lib.scanner.OnNetworkScanListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.e("TAG", "onCreate")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_host)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}