package com.hub.wifianalysis.ui.home

sealed class HomeUiEffect {
    data class NavigateToDetails(val ipAddress: String) : HomeUiEffect()
}
