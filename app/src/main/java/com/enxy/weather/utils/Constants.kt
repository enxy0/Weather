package com.enxy.weather.utils


enum class Temperature(val displayedName: String) {
    CELSIUS("C°"),
    FAHRENHEIT("F°");

    override fun toString(): String = displayedName
}

enum class Wind(val displayedName: String) {
    METERS_PER_SECOND("m/s"),
    KILOMETERS_PER_HOUR("km/h");

    override fun toString(): String = displayedName
}

enum class Pressure(val displayedName: String) {
    MILLIMETERS_OF_MERCURY("mmHg"),
    HECTO_PASCALS("hPa");

    override fun toString(): String = displayedName
}

enum class Theme(val displayedName: String) {
    LIGHT("Light"),
    NIGHT("Night");

    override fun toString(): String = displayedName
}

const val OPEN_WEATHER_MAP_URL = "https://api.openweathermap.org"
const val OPEN_CAGE_URL = "https://api.opencagedata.com/"