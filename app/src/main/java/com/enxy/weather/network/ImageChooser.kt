package com.enxy.weather.network

class ImageChooser {
    class OpenWeatherMap {
        companion object {
            fun getImagePairFromId(id: Int, dayPart: Char): Pair<String, String> {
                // dayPart - is always night or day: 'n' or 'd'
                // id - codes from https://openweathermap.org/weather-conditions
                val image: String
                val precipitation: String
                when (id) {
                    200 -> {
                        image = "thunderstorm"
                        precipitation = "light_rain"
                    }
                    201 -> {
                        image = "thunderstorm"
                        precipitation = "middle_rain"
                    }
                    202 -> {
                        image = "thunderstorm"
                        precipitation = "heavy_rain"
                    }
                    210 -> {
                        image = "thunderstorm"
                        precipitation = "light_rain"
                    }
                    211 -> {
                        image = "thunderstorm"
                        precipitation = "middle_rain"
                    }
                    212 -> {
                        image = "thunderstorm"
                        precipitation = "heavy_rain"
                    }
                    221 -> {
                        image = "thunderstorm"
                        precipitation = "empty"
                    }
                    230 -> {
                        image = "thunderstorm"
                        precipitation = "light_rain"
                    }
                    231 -> {
                        image = "thunderstorm"
                        precipitation = "middle_rain"
                    }
                    232 -> {
                        image = "thunderstorm"
                        precipitation = "heavy_rain"
                    }
                    300 -> {
                        image = "broken_clouds"
                        precipitation = "light_rain"
                    }
                    301 -> {
                        image = "broken_clouds"
                        precipitation = "middle_rain"
                    }
                    302 -> {
                        image = "broken_clouds"
                        precipitation = "heavy_rain"
                    }
                    310 -> {
                        image = "broken_clouds"
                        precipitation = "light_rain"
                    }
                    311 -> {
                        image = "broken_clouds"
                        precipitation = "middle_rain"
                    }
                    312 -> {
                        image = "broken_clouds"
                        precipitation = "heavy_rain"
                    }
                    313 -> {
                        image = "broken_clouds"
                        precipitation = "middle_rain"
                    }
                    314 -> {
                        image = "broken_clouds"
                        precipitation = "heavy_rain"
                    }
                    321 -> {
                        image = "broken_clouds"
                        precipitation = "middle_rain"
                    }
                    500 -> {
                        image = "cloudy_$dayPart"
                        precipitation = "light_rain"
                    }
                    501 -> {
                        image = "cloudy_$dayPart"
                        precipitation = "middle_rain"
                    }
                    502 -> {
                        image = "cloudy_$dayPart"
                        precipitation = "heavy_rain"
                    }
                    503 -> {
                        image = "cloudy_$dayPart"
                        precipitation = "heavy_rain"
                    }
                    504 -> {
                        image = "cloudy_$dayPart"
                        precipitation = "heavy_rain"
                    }
                    511 -> {
                        image = "broken_clouds"
                        precipitation = "middle_snow"
                    }
                    520 -> {
                        image = "broken_clouds"
                        precipitation = "light_rain"
                    }
                    521 -> {
                        image = "broken_clouds"
                        precipitation = "middle_rain"
                    }
                    522 -> {
                        image = "broken_clouds"
                        precipitation = "heavy_rain"
                    }
                    533 -> {
                        image = "broken_clouds"
                        precipitation = "middle_rain"
                    }
                    600 -> {
                        image = "broken_clouds"
                        precipitation = "light_snow"
                    }
                    601 -> {
                        image = "broken_clouds"
                        precipitation = "middle_snow"
                    }
                    602 -> {
                        image = "broken_clouds"
                        precipitation = "heavy_snow"
                    }
                    611 -> {
                        image = "broken_clouds"
                        precipitation = "snow_rain"
                    }
                    612 -> {
                        image = "broken_clouds"
                        precipitation = "snow_rain"
                    }
                    613 -> {
                        image = "broken_clouds"
                        precipitation = "snow_rain"
                    }
                    615 -> {
                        image = "broken_clouds"
                        precipitation = "snow_rain"
                    }
                    616 -> {
                        image = "broken_clouds"
                        precipitation = "snow_rain"
                    }
                    620 -> {
                        image = "broken_clouds"
                        precipitation = "light_snow"
                    }
                    621 -> {
                        image = "broken_clouds"
                        precipitation = "middle_snow"
                    }
                    622 -> {
                        image = "broken_clouds"
                        precipitation = "heavy_snow"
                    }
                    in 700..799 -> {
                        image = "scattered_clouds"
                        precipitation = "mist"
                    }
                    800 -> {
                        image = "clear_$dayPart"
                        precipitation = "empty"
                    }
                    801 -> {
                        image = "few_clouds_$dayPart"
                        precipitation = "empty"
                    }
                    802 -> {
                        image = "scattered_clouds"
                        precipitation = "empty"
                    }
                    803 -> {
                        image = "broken_clouds"
                        precipitation = "empty"
                    }
                    804 -> {
                        image = "broken_clouds"
                        precipitation = "empty"
                    }
                    else -> {
                        image = "not_found"
                        precipitation = "not_found"
                    }
                }
                return Pair("weather_$image", "precipitations_$precipitation")
            }
        }
    }

    class Yahoo {
        companion object {
            fun getImagePairFromId(imageId: Int): Pair<String, String> {
            val image: String
            val precipitation: String
            when (imageId) {
                0 -> {
                    // tornado (торнадо)
                    image = "scattered_clouds"
                    precipitation = "mist"
                }
                1 -> {
                    // tropical storm (тропическая буря)
                    image = "scattered_clouds"
                    precipitation = "mist"
                }
                2 -> {
                    // hurricane (ураган)
                    image = "scattered_clouds"
                    precipitation = "mist"
                }
                3 -> {
                    // severe thunderstorms (сильная гроза)
                    image = "thunderstorm"
                    precipitation = "middle_rain"
                }
                4 -> {
                    // thunderstorms (гроза)
                    image = "thunderstorm"
                    precipitation = "light_rain"
                }
                5 -> {
                    // mixed rain and snow (снег с дождем)
                    image = "broken_clouds"
                    precipitation = "snow_rain"
                }
                6 -> {
                    // mixed rain and sleet (мокрый снег и дождь)
                    image = "broken_clouds"
                    precipitation = "snow_rain"
                }
                7 -> {
                    // mixed snow and sleet (мокрый и обычный снег)
                    image = "broken_clouds"
                    precipitation = "snow_rain"
                }
                8 -> {
                    // freezing drizzle (леденящая изморось)
                    image = "broken_clouds"
                    precipitation = "middle_rain"
                }
                9 -> {
                    // drizzle (изморось)
                    image = "broken_clouds"
                    precipitation = "middle_rain"
                }
                10 -> {
                    // freezing rain (ледяной дождь)
                    image = "broken_clouds"
                    precipitation = "middle_snow"
                }
                11 -> {
                    // showers (ливень)
                    image = "broken_clouds"
                    precipitation = "middle_rain"
                }
                12 -> {
                    // showers (ливень)
                    image = "broken_clouds"
                    precipitation = "middle_rain"
                }
                13 -> {
                    // snow flurries (порывы снега)
                    image = "broken_clouds"
                    precipitation = "middle_snow"
                }
                14 -> {
                    // light snow showers (небольшой снегопад)
                    image = "broken_clouds"
                    precipitation = "light_snow"
                }
                15 -> {
                    // blowing snow (низовая метель)
                    image = "broken_clouds"
                    precipitation = "heavy_snow"
                }
                16 -> {
                    // snow (снег)
                    image = "broken_clouds"
                    precipitation = "middle_snow"
                }
                17 -> {
                    // hail (град)
                    image = "broken_clouds"
                    precipitation = "middle_snow"
                }
                18 -> {
                    // sleet (дождь со снегом)
                    image = "broken_clouds"
                    precipitation = "snow_rain"
                }
                19 -> {
                    // dust (пыль)
                    image = "scattered_clouds"
                    precipitation = "mist"
                }
                20 -> {
                    // foggy (Туман)
                    image = "scattered_clouds"
                    precipitation = "mist"
                }
                21 -> {
                    // haze (мгла)
                    image = "scattered_clouds"
                    precipitation = "mist"
                }
                22 -> {
                    // smoky (дымчатость)
                    image = "scattered_clouds"
                    precipitation = "mist"
                }
                23 -> {
                    // blustery (бушующий)
                    image = "scattered_clouds"
                    precipitation = "mist"
                }
                24 -> {
                    // windy (ветренно)
                    image = "scattered_clouds"
                    precipitation = "mist"
                }
                25 -> {
                    // cold (холодно)
                    image = "broken_clouds"
                    precipitation = "middle_snow"
                }
                26 -> {
                    // cloudy (облачно)
                    image = "few_clouds_d"
                    precipitation = "empty"
                }
                27 -> {
                    // mostly cloudy - night (в основном облачно - ночь)
                    image = "scattered_clouds"
                    precipitation = "empty"
                }
                28 -> {
                    // mostly cloudy - day (в основном облачно - день)
                    image = "scattered_clouds"
                    precipitation = "empty"
                }
                29 -> {
                    // partly cloudy - night (переменная облачность - ночь)
                    image = "few_clouds_n"
                    precipitation = "empty"
                }
                30 -> {
                    // partly cloudy - day (переменная облачность - день)
                    image = "few_clouds_d"
                    precipitation = "empty"
                }
                31 -> {
                    // clear - night (ясно - ночь)
                    image = "clear_n"
                    precipitation = "empty"
                }
                32 -> {
                    // sunny (солнечно)
                    image = "clear_d"
                    precipitation = "empty"
                }
                33 -> {
                    // 	fair - night (ясно, свтело - ночь)
                    image = "clear_n"
                    precipitation = "empty"
                }
                34 -> {
                    // 	fair - day (ясно, свтело - день)
                    image = "clear_d"
                    precipitation = "empty"
                }
                35 -> {
                    // mixed rain and hail (дождь с градом)
                    image = "broken_clouds"
                    precipitation = "middle_rain"
                }
                36 -> {
                    // hot (жара, очень солнечно ?)
                    image = "clear_d"
                    precipitation = "empty"
                }
                37 -> {
                    // isolated thunderstorms (гроза)
                    image = "thunderstorm"
                    precipitation = "middle_rain"
                }
                38 -> {
                    // scattered thunderstorms (гроза)
                    image = "thunderstorm"
                    precipitation = "light_rain"
                }
                39 -> {
                    // scattered thunderstorms (гроза)
                    image = "thunderstorm"
                    precipitation = "light_rain"
                }
                40 -> {
                    // scattered showers (местами дождь)
                    image = "broken_clouds"
                    precipitation = "middle_rain"
                }
                41 -> {
                    // 	heavy snow (сильный снегопад)
                    image = "broken_clouds"
                    precipitation = "heavy_snow"
                }
                42 -> {
                    // scattered snow showers (местами снегопад)
                    image = "broken_clouds"
                    precipitation = "middle_snow"
                }
                43 -> {
                    // 	heavy snow (сильный снегопад)
                    image = "broken_clouds"
                    precipitation = "heavy_snow"
                }
                44 -> {
                    // partly cloudy (переменная облачность)
                    image = "few_clouds_d"
                    precipitation = "empty"
                }
                45 -> {
                    // thundershowers (ливень)
                    image = "broken_clouds"
                    precipitation = "middle_rain"
                }
                46 -> {
                    // snow showers (снегопад)
                    image = "broken_clouds"
                    precipitation = "middle_snow"
                }
                47 -> {
                    // isolated thundershowers (местами ливень)
                    image = "broken_clouds"
                    precipitation = "middle_rain"
                }
                else -> {
                    // Not available (прогноз не доступен)
                    image = "not_found"
                    precipitation = "empty"
                }
            }
            return Pair("weather_$image", "weather_$precipitation")
//        return "android.resource://com.enxy.weatherclothing/drawable/weather_$mImageId"
        }
        }
    }
}