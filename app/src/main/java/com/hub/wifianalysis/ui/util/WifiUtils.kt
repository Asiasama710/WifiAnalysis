@file:Suppress("DEPRECATION")

package com.hub.wifianalysis.ui.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hub.wifianalysis.ui.util.model.WifiDetails
import java.io.IOException
import java.net.URL
import java.util.Scanner

/**
 * WifiUtils is an object that provides utility functions for working with Wi-Fi.
 */
object WifiUtils {
    private const val REQUEST_CODE_WIFI_PERMISSION = 1


    /**
     * Gets the details of the Wi-Fi connection.
     *
     * @param context The context to use.
     * @param callback The callback to call with the Wi-Fi details.
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getWifiDetails(context: Context, callback: (WifiDetails?) -> Unit) {
        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiManager =
            context.applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            // Wi-Fi is not enabled, return null
            callback(null)
            return
        }

        val wifiInfoManager = wifiManager.connectionInfo
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val wifiInfo = networkCapabilities?.transportInfo as? WifiInfo
                    // Rest of the code...
                    if (wifiInfo != null) {
                        val wifiDetails = getWifiSSID(context)?.let {
                            if (ActivityCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                ActivityCompat.requestPermissions(
                                        context as Activity,
                                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                        REQUEST_CODE_WIFI_PERMISSION
                                )
                            }
                            WifiDetails(
                                    publicIp = getPublicIP(),
                                    ipAddress = intToIp(wifiInfo.ipAddress),
                                    routerIp = getRouterIp(context),
                                    dns1 = getDns1(context),
                                    dns2 = getDns2(context),
                                    bssld = it,
                                    ssid = it,
                                    macAddress = wifiInfo.macAddress,
                                    linkSpeed = wifiInfo.linkSpeed,
                                    signalStrength = wifiInfo.rssi,
                                    frequency = wifiInfo.frequency,
                                    networkId = getNetworkId(context),
                                    connectionType = getConnectionType(context),
                                    isHiddenSsid = wifiInfo.hiddenSSID
                            )
                        }
                        callback(wifiDetails)
                    } else {
                        val wifiDetails = getWifiSSID(context)?.let {
                            WifiDetails(
                                    publicIp = getPublicIP(),
                                    ipAddress = intToIp(wifiInfoManager.ipAddress),
                                    routerIp = getRouterIp(context),
                                    dns1 = getDns1(context),
                                    dns2 = getDns2(context),
                                    bssld = wifiInfoManager.bssid,
                                    ssid = wifiInfoManager.ssid,
                                    macAddress = wifiInfoManager.macAddress,
                                    linkSpeed = wifiInfoManager.linkSpeed,
                                    signalStrength = wifiInfoManager.rssi,
                                    frequency = wifiInfoManager.frequency,
                                    networkId = getNetworkId(context),
                                    connectionType = getConnectionType(context),
                                    isHiddenSsid = wifiInfoManager.hiddenSSID
                            )
                        }
                        Log.e("TAG", " => Q: #@$wifiDetails", )
                        callback(wifiDetails)
                    }
                } else {
                    Log.e("TAG", "onAvailable: #@" )
                    val wifiDetails = getWifiSSID(context)?.let {
                        WifiDetails(
                                publicIp = getPublicIP(),
                                ipAddress = intToIp(wifiInfoManager.ipAddress),
                                routerIp = getRouterIp(context),
                                dns1 = getDns1(context),
                                dns2 = getDns2(context),
                                bssld = wifiInfoManager.bssid,
                                ssid = wifiInfoManager.ssid,
                                macAddress = wifiInfoManager.macAddress,
                                linkSpeed = wifiInfoManager.linkSpeed,
                                signalStrength = wifiInfoManager.rssi,
                                frequency = wifiInfoManager.frequency,
                                networkId = getNetworkId(context),
                                connectionType = getConnectionType(context),
                                isHiddenSsid = wifiInfoManager.hiddenSSID
                        )
                    }
                    Log.e("TAG", "onAvailable: #@$wifiDetails", )
                    callback(wifiDetails)
                    // Alternative code for devices running on lower than Android 10
                }


            }
        }
        connectivityManager.requestNetwork(request, networkCallback)
    }

    /**
     * Gets the details of the Wi-Fi connection.
     *
     * @param context The context to use.
     * @param callback The callback to call with the Wi-Fi details.
     */
    fun getPublicIP(): String {
        var publicIP = ""
        try {
            val scanner = Scanner(
                URL("https://api.ipify.org")
                    .openStream(), "UTF-8"
            ).useDelimiter("\\A")
            publicIP = scanner.next()
            println("My current IP address is $publicIP")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return publicIP
    }


    /**
     * Gets the SSID of the Wi-Fi connection.
     *
     * @param context The context to use.
     * @return The SSID of the Wi-Fi connection, or null if not connected to Wi-Fi.
     */
    fun getWifiSSID(context: Context): String? {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo

        return if (wifiManager.isWifiEnabled) {
            wifiInfo.ssid
        } else {
            null
        }
    }

    /**
     * Gets the network ID of the Wi-Fi connection.
     *
     * @param context The context to use.
     * @return The network ID of the Wi-Fi connection, or -1 if not connected to Wi-Fi.
     */
    fun getNetworkId(context: Context): Int {
        // Check ACCESS_FINE_LOCATION permission
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            return if (wifiManager.isWifiEnabled && wifiInfo.networkId != -1) {
                wifiInfo.networkId
            } else {
                -1
            }
        } else {
            return -1
        }
    }


    /**
     * Gets the IP address of the router.
     *
     * @param context The context to use.
     * @return The IP address of the router.
     */
    private fun getRouterIp(context: Context): String {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val dhcpInfo = wifiManager.dhcpInfo
        return intToIp(dhcpInfo.gateway)
    }

    /**
     * Gets the first DNS server IP address.
     *
     * @param context The context to use.
     * @return The first DNS server IP address.
     */
    private fun getDns1(context: Context): String {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val dhcpInfo = wifiManager.dhcpInfo
        return intToIp(dhcpInfo.dns1)
    }

    /**
     * Gets the second DNS server IP address.
     *
     * @param context The context to use.
     * @return The second DNS server IP address.
     */
    private fun getDns2(context: Context): String {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val dhcpInfo = wifiManager.dhcpInfo
        return intToIp(dhcpInfo.dns2)
    }

    /**
     * Converts an integer to an IP address.
     *
     * @param ip The integer to convert.
     * @return The IP address.
     */
    private fun intToIp(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                ((ip shr 8) and 0xFF) + "." +
                ((ip shr 16) and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }

    /**
     * This function gets the type of the current network connection.
     * It requires the Android Q (API 29) or higher.
     *
     * @param context The context to use.
     * @return The type of the current network connection as a String.
     */
private fun getConnectionType(context: Context): String {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        networkCapabilities?.run {
            when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi"
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
                else -> "Unknown"
            }
        } ?: "Unknown"
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo?.run {
            when (type) {
                ConnectivityManager.TYPE_WIFI -> "Wi-Fi"
                ConnectivityManager.TYPE_MOBILE -> "Cellular"
                else -> "Unknown"
            }
        } ?: "Unknown"
    }
}
}