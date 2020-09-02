package com.enxy.weather.utils.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Formats Unix timestamp to the given [pattern]
 * Don't forget that timestamp = unixTimestamp * 1000L
 */
fun Long.formatTo(pattern: String, locale: Locale): String {
    return SimpleDateFormat(pattern, locale).format(Date(this * 1000L))
}

/**
 * Helper function for creating capitalized days from string (Sunday, Monday...)
 */
fun Long.toCapitalizedDay(locale: Locale): String {
    return formatTo("EEEE", locale).capitalize(locale)
}

/**
 * Helper function for creating date in format: dd.MM (01.12, 06.09, ...)
 */
fun Long.toDayMonth(locale: Locale): String {
    return formatTo("dd.MM", locale)
}

/**
 * Helper function for create time in format: HH:mm (13:24, 23:59, ...)
 */
fun Long.toHourMinutes(locale: Locale): String {
    return formatTo("HH:mm", locale)
}