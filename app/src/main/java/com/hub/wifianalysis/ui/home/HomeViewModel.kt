package com.hub.wifianalysis.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hub.wifianalysis.ui.base.BaseInteractionListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel() : ViewModel(), BaseInteractionListener {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        Log.e("TAG", "Home view model: ")
    }

}