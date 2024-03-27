package com.hub.wifianalysis.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
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

/**
 * MainActivity is the main activity of the application.
 *
 * This activity is responsible for setting up the navigation drawer and handling navigation events.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = binding.drawerLayout
        navigationView = binding.navView

        setupNavigation()
        setupDrawerToggle()
    }

    /**
     * Sets up the navigation for the application.
     */
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

    /**
     * Sets up the drawer toggle for the navigation drawer.
     */
    private fun setupDrawerToggle() {
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    /**
     * Navigates to the destination associated with the given item ID.
     *
     * @param itemId The ID of the item to navigate to.
     */
    private fun navigateToDestination(itemId: Int) {
        when (itemId) {
            R.id.connected_devices -> navController.navigate(R.id.devicesFragment)
            R.id.ports_statistics -> navController.navigate(R.id.portsStatisticsFragment)
            R.id.password_checker -> navController.navigate(R.id.passwordCheckerFragment)
            R.id.home -> navController.navigate(R.id.home_fragment)
        }
    }

    /**
     * Called when the activity's options menu needs to be created.
     *
     * @return Boolean Return true for the menu to be displayed; if you return false it will not be shown.
     */
    override fun onSupportNavigateUp(): Boolean {
        setNavigationIconColor(R.color.white)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
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

    /**
     * Sets the color of the navigation icon.
     *
     * @param colorRes The resource ID of the color to set.
     */
    private fun setNavigationIconColor(colorRes: Int) {
        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_slider)
        drawable?.let {
            val wrappedDrawable = DrawableCompat.wrap(it)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(this, colorRes))
            supportActionBar?.setHomeAsUpIndicator(wrappedDrawable)
        }
    }
}