package com.enxy.weather.utils.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Formats Unix timestamp to the given [pattern]
 * Don't forget that timestamp = unixTimestamp * 1000L
 */
infix fun Long.formatTo(pattern: String): String =
    SimpleDateFormat(pattern, Locale.US).format(Date(this * 1000L))
