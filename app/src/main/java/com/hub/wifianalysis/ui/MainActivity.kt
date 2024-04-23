package com.hub.wifianalysis.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.hub.wifianalysis.R
import com.hub.wifianalysis.databinding.ActivityMainBinding
import com.hub.wifianalysis.ui.home.HomeFragment
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(!isLocationEnabled(this)){
            Toast.makeText(this, "turn on the Location pleas", Toast.LENGTH_SHORT).show()
            openLocationSettings()
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupPermissionRequest()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = binding.drawerLayout
        navigationView = binding.navView


        setupNavigation()
        setupDrawerToggle()


    }


    private fun setupPermissionRequest() {
        PermissionX.init(this)
            .permissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
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
                  //
                } else {
                    showPermissionDeniedDialog()
                    Toast.makeText(
                            this,
                            "These permissions are denied: $deniedList",
                            Toast.LENGTH_LONG
                    ).show()
                }
            }
    }


    private fun showPermissionDeniedDialog() {
        val builder = AlertDialog.Builder(this)
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
    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )
    }

    private fun openLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivityForResult(intent, HomeFragment.REQUEST_CODE_APP_SETTINGS)
    }

    @Suppress("DEPRECATION")
    private fun setupNavigation() {
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.devicesFragment,
            R.id.portsStatisticsFragment,
            R.id.passwordCheckerFragment,
            R.id.home_fragment
        ).setDrawerLayout(drawerLayout)
            .build()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            navigateToDestination(menuItem.itemId)
            true
        }
    }

    private fun setupDrawerToggle() {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun navigateToDestination(itemId: Int) {
        when (itemId) {
            R.id.connected_devices -> navController.navigate(R.id.devicesFragment)
            R.id.ports_statistics -> navController.navigate(R.id.portsStatisticsFragment)
            R.id.password_checker -> navController.navigate(R.id.passwordCheckerFragment)
            R.id.home -> navController.navigate(R.id.home_fragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        setNavigationIconColor(R.color.white)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                } else if (navController.currentDestination?.id in listOf(
                        R.id.devicesFragment,
                        R.id.portsStatisticsFragment,
                        R.id.passwordCheckerFragment
                    )
                ) {
                    navController.popBackStack(R.id.home_fragment, false)
                    true
                } else {
                    super.onOptionsItemSelected(item)
                }
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setNavigationIconColor(colorRes: Int) {
        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_slider)
        drawable?.let {
            val wrappedDrawable = DrawableCompat.wrap(it)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(this, colorRes))
            supportActionBar?.setHomeAsUpIndicator(wrappedDrawable)
        }
    }
}
