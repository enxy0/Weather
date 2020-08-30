package com.enxy.weather.utils


enum class TemperatureUnit(val displayedName: String) {
    CELSIUS("C°"),
    FAHRENHEIT("F°")
}

enum class WindUnit(val displayedName: String) {
    METERS_PER_SECOND("m/s"),
    KILOMETERS_PER_HOUR("km/h")
}

enum class PressureUnit(val displayedName: String) {
    MILLIMETERS_OF_MERCURY("mmHg"),
    HECTO_PASCALS("hPa")
}

enum class Theme(val displayedName: String) {
    LIGHT("Light"),
    NIGHT("Night")
}

const val OPEN_WEATHER_MAP_URL = "https://api.openweathermap.org"
const val OPEN_CAGE_URL = "https://api.opencagedata.com/"