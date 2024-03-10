package com.hub.wifianalysis.ui.util

import android.graphics.Color
import kotlin.random.Random

fun getRandomColor(): Int {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color.rgb(red, green, blue)
}