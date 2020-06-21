package com.enxy.weather.data.network

import androidx.annotation.DrawableRes
import com.enxy.weather.R

const val DAY = 'd'
const val NIGHT = 'n'

/**
 * Returns an image (for Dark Surfaces) from [R.drawable] based on:
 * [id] (weather code, check the link)
 * [dayPart] ('n' - night, 'd' - day)
 * [link](https://openweathermap.org/weather-conditions)
 */
@DrawableRes fun getIconDarkSurface(id: Int, dayPart: Char): Int = when (id) {
    200 -> R.drawable.weather_thunderstorm_rain_light
    201 -> R.drawable.weather_thunderstorm_rain_middle
    202 -> R.drawable.weather_thunderstorm_rain_heavy
    210 -> R.drawable.weather_thunderstorm_rain_light
    211 -> R.drawable.weather_thunderstorm_rain_middle
    212 -> R.drawable.weather_thunderstorm_rain_heavy
    221 -> R.drawable.weather_thunderstorm_rain_light
    230 -> R.drawable.weather_thunderstorm_rain_light
    231 -> R.drawable.weather_thunderstorm_rain_middle
    232 -> R.drawable.weather_thunderstorm_rain_heavy
    300 -> R.drawable.weather_rain_light
    301 -> R.drawable.weather_rain_middle
    302 -> R.drawable.weather_rain_middle
    310 -> R.drawable.weather_rain_light
    311 -> R.drawable.weather_rain_middle
    312 -> R.drawable.weather_rain_heavy
    313 -> R.drawable.weather_rain_middle
    314 -> R.drawable.weather_rain_heavy
    321 -> R.drawable.weather_rain_middle
    500 -> when (dayPart) {
        DAY -> R.drawable.weather_day_cloudy_rain_light
        else -> R.drawable.weather_night_cloudy_rain_light
    }
    501 -> when (dayPart) {
        DAY -> R.drawable.weather_day_cloudy_rain_middle
        else -> R.drawable.weather_night_cloudy_rain_middle
    }
    502 -> R.drawable.weather_rain_heavy
    503 -> R.drawable.weather_rain_heavy
    504 -> R.drawable.weather_rain_heavy
    511 -> R.drawable.weather_snow_middle
    520 -> R.drawable.weather_rain_light
    521 -> R.drawable.weather_rain_middle
    522 -> R.drawable.weather_rain_heavy
    533 -> R.drawable.weather_rain_middle
    600 -> R.drawable.weather_snow_light
    601 -> R.drawable.weather_snow_middle
    602 -> R.drawable.weather_snow_heavy
    in 611..616 -> R.drawable.weather_snow_middle // TODO: Add icon - snow and rain
    620 -> R.drawable.weather_snow_light
    621 -> R.drawable.weather_snow_middle
    622 -> R.drawable.weather_snow_heavy
    in 700..799 -> R.drawable.weather_mist
    800 -> when (dayPart) {
        DAY -> R.drawable.weather_clear_day
        else -> R.drawable.weather_clear_night
    }
    801 -> when (dayPart) {
        DAY -> R.drawable.weather_day_cloudy
        else -> R.drawable.weather_night_cloudy
    }
    802 -> R.drawable.weather_scattered_clouds
    803 -> R.drawable.weather_broken_clouds
    804 -> R.drawable.weather_broken_clouds
    else -> R.drawable.weather_mist // TODO: Add icon - unknown weather conditions
}

/**
 * Returns an image (for Light Surfaces) from [R.drawable] based on:
 * [id] (weather code, check the link)
 * [dayPart] ('n' - night, 'd' - day)
 * [link](https://openweathermap.org/weather-conditions)
 */
@DrawableRes fun getIconLightSurface(id: Int, dayPart: Char): Int = when (id) {
    200 -> R.drawable.current_weather_thunderstorm_rain_light
    201 -> R.drawable.current_weather_thunderstorm_rain_middle
    202 -> R.drawable.current_weather_thunderstorm_rain_heavy
    210 -> R.drawable.current_weather_thunderstorm_rain_light
    211 -> R.drawable.current_weather_thunderstorm_rain_middle
    212 -> R.drawable.current_weather_thunderstorm_rain_heavy
    221 -> R.drawable.current_weather_thunderstorm_rain_light
    230 -> R.drawable.current_weather_thunderstorm_rain_light
    231 -> R.drawable.current_weather_thunderstorm_rain_middle
    232 -> R.drawable.current_weather_thunderstorm_rain_heavy
    300 -> R.drawable.current_weather_rain_light
    301 -> R.drawable.current_weather_rain_middle
    302 -> R.drawable.current_weather_rain_middle
    310 -> R.drawable.current_weather_rain_light
    311 -> R.drawable.current_weather_rain_middle
    312 -> R.drawable.current_weather_rain_heavy
    313 -> R.drawable.current_weather_rain_middle
    314 -> R.drawable.current_weather_rain_heavy
    321 -> R.drawable.current_weather_rain_middle
    500 -> when (dayPart) {
        DAY -> R.drawable.current_weather_day_cloudy_rain_light
        else -> R.drawable.current_weather_night_cloudy_rain_light
    }
    501 -> when (dayPart) {
        DAY -> R.drawable.current_weather_day_cloudy_rain_middle
        else -> R.drawable.current_weather_night_cloudy_rain_middle
    }
    502 -> R.drawable.current_weather_rain_heavy
    503 -> R.drawable.current_weather_rain_heavy
    504 -> R.drawable.current_weather_rain_heavy
    511 -> R.drawable.current_weather_snow_middle
    520 -> R.drawable.current_weather_rain_light
    521 -> R.drawable.current_weather_rain_middle
    522 -> R.drawable.current_weather_rain_heavy
    533 -> R.drawable.current_weather_rain_middle
    600 -> R.drawable.current_weather_snow_light
    601 -> R.drawable.current_weather_snow_middle
    602 -> R.drawable.current_weather_snow_heavy
    in 611..616 -> R.drawable.current_weather_snow_middle // TODO: Add icon - snow and rain
    620 -> R.drawable.current_weather_snow_light
    621 -> R.drawable.current_weather_snow_middle
    622 -> R.drawable.current_weather_snow_heavy
    in 700..799 -> R.drawable.current_weather_mist
    800 -> when (dayPart) {
        DAY -> R.drawable.weather_clear_day
        else -> R.drawable.weather_clear_night
    }
    801 -> when (dayPart) {
        DAY -> R.drawable.current_weather_day_cloudy
        else -> R.drawable.current_weather_night_cloudy
    }
    802 -> R.drawable.current_weather_scattered_clouds
    803 -> R.drawable.current_weather_broken_clouds
    804 -> R.drawable.current_weather_broken_clouds
    else -> R.drawable.current_weather_mist // TODO: Add icon - unknown weather conditions
}

