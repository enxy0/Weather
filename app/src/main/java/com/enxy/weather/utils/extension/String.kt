package com.enxy.weather.utils.extension

fun String.withSign(): String = when {
    this.toInt() > 0 -> "+$this"
    else -> this
}