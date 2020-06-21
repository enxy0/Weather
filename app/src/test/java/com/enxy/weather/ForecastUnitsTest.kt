package com.enxy.weather

import com.enxy.weather.data.entity.CurrentForecast
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.HourForecast
import com.enxy.weather.utils.Pressure.HECTO_PASCALS
import com.enxy.weather.utils.Pressure.MILLIMETERS_OF_MERCURY
import com.enxy.weather.utils.Temperature.CELSIUS
import com.enxy.weather.utils.Temperature.FAHRENHEIT
import com.enxy.weather.utils.Wind.KILOMETERS_PER_HOUR
import com.enxy.weather.utils.Wind.METERS_PER_SECOND
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ForecastUnitsTest {
    private val forecast = Forecast(
        locationName = "Moscow, Central Administrative Okrug, Russia",
        longitude = 37.6174943,
        latitude = 55.7504461,
        timestamp = Calendar.getInstance(),
        wasOpenedLast = false,
        isFavourite = false,
        currentForecast = CurrentForecast(
            currentForecastId = 0,
            temperature = "+21",
            description = "Broken clouds",
            feelsLike = "18",
            wind = "5",
            pressure = "1022",
            humidity = "56",
            imageId = 2131230822
        ),
        hourForecastList = arrayListOf(
            HourForecast(0, "+21", "15:00", 1),
            HourForecast(1, "+19", "18:00", 2),
            HourForecast(2, "+16", "21:00", 3),
            HourForecast(3, "+15", "23:00", 4)
        )
    )

    @Test fun testDefaultUnits() {
        assertEquals(forecast, forecast.inUnits(CELSIUS, METERS_PER_SECOND, HECTO_PASCALS))
    }

    @Test fun testTemperatureUnits() {
        val updatedForecast = forecast.inUnits(temperatureUnit = FAHRENHEIT)
        assertEquals("+70", updatedForecast.currentForecast.temperature)
        assertEquals("+64", updatedForecast.currentForecast.feelsLike)
        assertEquals("+70", updatedForecast.hourForecastList[0].temperature)
        assertEquals("+66", updatedForecast.hourForecastList[1].temperature)
        assertEquals("+61", updatedForecast.hourForecastList[2].temperature)
        assertEquals("+59", updatedForecast.hourForecastList[3].temperature)
    }

    @Test fun testWindUnits() {
        val updatedForecast = forecast.inUnits(windUnit = KILOMETERS_PER_HOUR)
        assertEquals("18", updatedForecast.currentForecast.wind)
    }

    @Test fun testPressureUnits() {
        val updatedForecast = forecast.inUnits(pressureUnit = MILLIMETERS_OF_MERCURY)
        assertEquals("767", updatedForecast.currentForecast.pressure)
    }
}
