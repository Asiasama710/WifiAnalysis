package com.hub.wifianalysis.ui.util

import android.graphics.Color
import kotlin.random.Random

/**
 * Generates a random color.
 *
 * This function generates a random color by generating random values for red, green, and blue components.
 * Each component is a random integer between 0 and 255. These values are then combined to form a color.
 *
 * @return The generated random color as an Int.
 */
fun getRandomColor(): Int {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    return Color.rgb(red, green, blue)
}