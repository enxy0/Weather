package com.enxy.weather.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.enxy.weather.utils.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastDaoTest {
    private val database = appDatabase
    private val forecastDao = database.getForecastDao()

    @Before fun createDb() = runBlocking {
        for (forecast in forecasts)
            forecastDao.insertForecast(forecast)
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testAddedForecasts() = runBlocking {
        assertFalse(forecastDao.getForecasts().isEmpty())
        assertEquals(3, forecastDao.getForecasts().size)
    }

    @Test fun testFavouriteForecasts() = runBlocking {
        val savedForecasts = forecastDao.getFavouriteForecasts()

        // Size of favourite forecasts pre-defined: 2
        assertEquals(2, savedForecasts.size)

        // Checking each value of isFavourite
        assertTrue(savedForecasts[0].isFavourite)
        assertTrue(savedForecasts[1].isFavourite)
    }

    @Test fun testLastOpenedForecast() = runBlocking {
        // Checking that Saint-Petersburg forecast is the current forecast (wasOpenedLast=true)
        assertEquals(spbForecast.locationName, forecastDao.getCurrentForecast()!!.locationName)
    }

    @Test fun testUpdateLastOpenedForecast() = runBlocking {
        // Changing current forecast to Paris
        forecastDao.updateCurrentForecast(parisForecast.locationName)

        // Getting Saint-Petersburg forecast from Dao
        val spb = forecastDao.getForecasts().find {
            it.locationName == spbForecast.locationName
        }

        // Checking wasOpenedLast of SPb, since Paris is current now (wasOpenedLast=true)
        assertFalse(spb!!.wasOpenedLast)

        // Making sure Paris is the current forecast
        assertEquals(parisForecast.locationName, forecastDao.getCurrentForecast()!!.locationName)
    }

    @Test fun testChangeFavouriteForecast() = runBlocking {
        // Changing favourite status of Moscow to true
        forecastDao.setForecastFavouriteStatus(moscowForecast.locationName, true)

        // Getting Moscow forecast from Dao
        val moscow = forecastDao.getForecasts().find {
            it.locationName == moscowForecast.locationName
        }

        // Checking that isFavourite value changed
        assertTrue(moscow!!.isFavourite)

        // Changing favourite status of Paris to false
        forecastDao.setForecastFavouriteStatus(parisForecast.locationName, false)

        // Getting Paris forecast from Dao
        val paris = forecastDao.getForecasts().find {
            it.locationName == parisForecast.locationName
        }

        // Checking that isFavourite value changed
        assertFalse(paris!!.isFavourite)
    }
}