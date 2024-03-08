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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.hub.wifianalysis.model.WifiDetails

object WifiUtils {
    private const val REQUEST_CODE_WIFI_PERMISSION = 1


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
   suspend fun getWifiDetails(context: Context, callback: (WifiDetails?) -> Unit) {
        val request = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiManager = context.applicationContext.getSystemService(AppCompatActivity.WIFI_SERVICE) as WifiManager
        val wifiInfoManager =wifiManager.connectionInfo
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onAvailable(network: Network) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                val wifiInfo = networkCapabilities?.transportInfo as? WifiInfo
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
                        else{
                            Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show()
                        }
                        WifiDetails(
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
                    callback(wifiDetails)
                }
            }
        }
        connectivityManager.requestNetwork(request, networkCallback)
    }


    fun getWifiSSID(context: Context): String? {
        return if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo

            if (wifiManager.isWifiEnabled) {
                wifiInfo.ssid
            } else {
                null
            }
        } else {
            null
        }
    }

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




    private fun getRouterIp(context: Context): String {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val dhcpInfo = wifiManager.dhcpInfo
        return intToIp(dhcpInfo.gateway)
    }

    private fun getDns1(context: Context): String {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val dhcpInfo = wifiManager.dhcpInfo
        return intToIp(dhcpInfo.dns1)
    }

    private fun getDns2(context: Context): String {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val dhcpInfo = wifiManager.dhcpInfo
        return intToIp(dhcpInfo.dns2)
    }

    private fun intToIp(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                ((ip shr 8) and 0xFF) + "." +
                ((ip shr 16) and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getConnectionType(context: Context): String {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val connectionType = networkCapabilities?.run {
            when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi"
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
                else -> "Hidden"
            }
        } ?: "Unknown"
        return connectionType
    }
}