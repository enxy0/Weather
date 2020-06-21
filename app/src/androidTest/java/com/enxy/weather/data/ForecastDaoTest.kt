package com.enxy.weather.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.enxy.weather.utils.appDatabase
import com.enxy.weather.utils.forecasts
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

    @Test fun testSavedForecasts() = runBlocking {
        assertEquals(true, forecastDao.hasCachedForecasts())
    }

    @Test fun testFavouriteForecasts() = runBlocking {
        assertEquals(2, forecastDao.getFavouriteForecastList()!!.size)
        assertTrue(forecastDao.getFavouriteForecastList()!![0].isFavourite)
        assertTrue(forecastDao.getFavouriteForecastList()!![1].isFavourite)
    }

    @Test fun testIsForecastCached() = runBlocking {
        assertTrue(forecastDao.isForecastCached(forecasts[0].locationName))
        assertTrue(forecastDao.isForecastCached(forecasts[1].locationName))
        assertTrue(forecastDao.isForecastCached(forecasts[2].locationName))
    }

    @Test fun testLastOpenedForecast() = runBlocking {
        assertTrue(forecastDao.getLastOpenedForecast().wasOpenedLast)
    }

    @Test fun testUpdateLastOpenedForecast() = runBlocking {
        forecastDao.updateLastOpenedForecast("Paris, France")
        assertFalse(forecastDao.getForecastByLocationName("Saint-Petersburg, Russia").wasOpenedLast)
        assertEquals("Paris, France", forecastDao.getLastOpenedForecast().locationName)
    }

    @Test fun testChangeIsFavouriteStatus() = runBlocking {
        forecastDao.setForecastFavouriteStatus(37.6174943, 55.7504461, true)
        assertTrue(forecastDao.getForecastByLocationName("Moscow, Central Administrative Okrug, Russia").isFavourite)
        forecastDao.setForecastFavouriteStatus(2.3522219, 48.856614, false)
        assertFalse(forecastDao.getForecastByLocationName("Paris, France").isFavourite)
    }

    @Test fun testDeleteForecast() = runBlocking {
        forecastDao.deleteForecast(forecasts[0])
        assertNull(forecastDao.getForecastByLocationName("Moscow, Central Administrative Okrug, Russia"))
    }
}