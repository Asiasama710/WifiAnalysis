package com.hub.wifianalysis.ui.home

sealed class HomeUiEffect {
    data class NavigateToDetails(val ipAddress: String,val macAddress:String,val deviceName:String,val vendor:String) : HomeUiEffect()
}
