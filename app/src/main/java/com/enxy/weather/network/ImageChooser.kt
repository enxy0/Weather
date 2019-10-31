package com.enxy.weather.network

import com.enxy.weather.R

class ImageChooser {
    class OpenWeatherMap {
        companion object {
            fun getImagePairFromId(id: Int, dayPart: Char): Int {
                /*
                 * dayPart - is always night or day: 'n' or 'd'
                 * id - codes you can find here https://openweathermap.org/weather-conditions
                 */
                return when (id) {
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
                        'd' -> R.drawable.weather_day_cloudy_rain_light
                        else -> R.drawable.weather_night_cloudy_rain_light
                    }

                    501 -> when (dayPart) {
                        'd' -> R.drawable.weather_day_cloudy_rain_middle
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

                    611 -> {
                        // TODO: Add image for this code - snow and rain
                        R.drawable.weather_snow_middle
                    }
                    612 -> {
                        // TODO: Add image for this code - snow and rain
                        R.drawable.weather_snow_middle
                    }
                    613 -> {
                        // TODO: Add image for this code - snow and rain
                        R.drawable.weather_snow_middle
                    }
                    615 -> {
                        // TODO: Add image for this code - snow and rain
                        R.drawable.weather_snow_middle
                    }
                    616 -> {
                        // TODO: Add image for this code - snow and rain
                        R.drawable.weather_snow_middle
                    }
                    620 -> R.drawable.weather_snow_light

                    621 -> R.drawable.weather_snow_middle

                    622 -> R.drawable.weather_snow_heavy

                    in 700..799 -> R.drawable.weather_mist

                    800 -> when (dayPart) {
                        'd' -> R.drawable.weather_clear_day
                        else -> R.drawable.weather_clear_night
                    }

                    801 -> when (dayPart) {
                        'd' -> R.drawable.weather_day_cloudy
                        else -> R.drawable.weather_night_cloudy
                    }

                    802 -> R.drawable.weather_scattered_clouds

                    803 -> R.drawable.weather_broken_clouds

                    804 -> R.drawable.weather_broken_clouds

                    else -> {
                        // TODO: Add image for this code - unknown weather conditions
                        R.drawable.weather_mist
                    }
                }
            }
        }
    }
}