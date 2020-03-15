package com.enxy.weather.utils

import kotlin.math.roundToInt

private const val millimeterOfMercury: Double = 133.3223684

fun mmHgToHectoPascals(mmHg: Int): Int = (mmHg / millimeterOfMercury * 100).roundToInt()

fun hectoPascalsToMmHg(hectoPascals: Int): Int =
    (hectoPascals / 100 * millimeterOfMercury).roundToInt()

fun celsiusToFahrenheit(celsius: Int): Int = (celsius * 1.8 + 32).roundToInt()

fun fahrenheitToCelsius(fahrenheit: Int): Int = ((fahrenheit - 32) / 1.8).roundToInt()

fun metersPerSecToKilometersPerHour(metersPerSec: Int): Int = metersPerSec * 60 * 60 / 1000

fun kilometersPerHourToMetersPerSec(kilometersPerHour: Int): Int =
    kilometersPerHour * 1000 / (60 * 60)