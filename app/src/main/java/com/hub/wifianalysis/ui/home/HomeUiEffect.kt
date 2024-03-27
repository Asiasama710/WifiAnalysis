package com.hub.wifianalysis.ui.home

/**
 * HomeUiEffect is a sealed class that represents the different UI effects that can occur on the Home screen.
 * Currently, it only includes a navigation effect to the Details screen.
 */
sealed class HomeUiEffect {

    /**
     * NavigateToDetails is a data class that represents the navigation effect to the Details screen.
     * It includes properties for IP address, MAC address, device name, and vendor.
     *
     * @property ipAddress A String that represents the IP address of the device.
     * @property macAddress A String that represents the MAC address of the device.
     * @property deviceName A String that represents the name of the device.
     * @property vendor A String that represents the vendor of the device.
     */
    data class NavigateToDetails(val ipAddress: String,val macAddress:String,val deviceName:String,val vendor:String) : HomeUiEffect()
}