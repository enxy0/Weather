package com.enxy.weather.utils.extension

import kotlin.math.roundToInt

fun Double.withSign(): String = when {
    this > 0 -> "+${this.roundToInt()}"
    else -> this.roundToInt().toString()
}