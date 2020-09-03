package com.enxy.weather.data.weather

import com.enxy.weather.BuildConfig
import com.enxy.weather.data.entity.*
import com.enxy.weather.data.network.WeatherApi
import com.enxy.weather.data.network.getIconDarkSurface
import com.enxy.weather.data.network.getIconLightSurface
import com.enxy.weather.data.network.json.openweathermap.Current
import com.enxy.weather.data.network.json.openweathermap.Daily
import com.enxy.weather.data.network.json.openweathermap.Hourly
import com.enxy.weather.data.network.json.openweathermap.WeatherResponse
import com.enxy.weather.utils.Result
import com.enxy.weather.utils.extension.toCapitalizedDay
import com.enxy.weather.utils.extension.toDayMonth
import com.enxy.weather.utils.extension.toHourMinutes
import java.util.*
import kotlin.math.roundToInt

class OpenWeatherMapDataSource(private val weatherApi: WeatherApi) : WeatherDataSource {

    companion object {
        // OpenWeatherMap API default constants
        private const val OPEN_WEATHER_MAP_APPID = BuildConfig.API_KEY_OPEN_WEATHER_MAP
        private const val DEFAULT_UNITS = "metric"
        private const val HOURLY_COUNT = 24
        private const val DAILY_COUNT = 7
    }

    override suspend fun request(location: Location, locale: Locale): Result<Forecast> =
        safeApiCall(
            call = {
                weatherApi.getForecast(
                    longitude = location.longitude,
                    latitude = location.latitude,
                    APPID = OPEN_WEATHER_MAP_APPID,
                    language = locale.language,
                    units = DEFAULT_UNITS
                )
            },
            params = locale,
            transform = ::transformToForecast
            // Applying old location name, because weather requested by lon and lat
            // and it may be changed
        ).onSuccess { it.locationName = location.locationName }

    /**
     * Converts [WeatherResponse] to the [Forecast]
     */
    private fun transformToForecast(response: WeatherResponse, locale: Locale) = Forecast(
        locationName = response.timezone, // temporary name that will be changed
        longitude = response.lon,
        latitude = response.lat,
        currentForecast = response.current.toCurrentForecast(locale),
        hourForecastList = response.hourly.toHourList(locale),
        dayForecastList = response.daily.toDayList(locale),
    )

    private fun Current.toCurrentForecast(locale: Locale) =
        CurrentForecast(
            temperature = Temperature(temp.roundToInt()),
            feelsLike = Temperature(feelsLike.roundToInt()),
            description = weather[0].description.capitalize(locale),
            wind = Wind(windSpeed.roundToInt()),
            humidity = humidity,
            pressure = Pressure(pressure),
            imageId = getIconLightSurface(weather[0].id, weather[0].icon.last())
        )

    private fun List<Hourly>.toHourList(locale: Locale) = take(HOURLY_COUNT).map {
        HourForecast(
            temperature = Temperature(it.temperature.roundToInt()),
            time = it.timestamp.toHourMinutes(locale),
            imageId = getIconDarkSurface(it.weather[0].id, it.weather[0].icon.last())
        )
    }

    private fun List<Daily>.toDayList(locale: Locale) = take(DAILY_COUNT).map {
        DayForecast(
            highestTemp = Temperature(it.temperature.max.roundToInt()),
            lowestTemp = Temperature(it.temperature.min.roundToInt()),
            day = it.timestamp.toCapitalizedDay(locale),
            date = it.timestamp.toDayMonth(locale),
            wind = Wind(it.windSpeed.roundToInt()),
            pressure = Pressure(it.pressure),
            humidity = it.humidity,
            imageId = getIconLightSurface(it.weather[0].id, it.weather[0].icon.last())
        )
    }
}