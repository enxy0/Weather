package com.enxy.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.enxy.weather.utils.appDatabase
import com.enxy.weather.utils.getValue
import com.enxy.weather.utils.weatherViewModel
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherViewModelTest {
    private val viewModel = weatherViewModel
    private val database = appDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @After fun close() {
        database.close()
    }

    @Test fun testDefaultValues() {
        assertTrue(getValue(viewModel.isAppFirstLaunched))
        assertFalse(getValue(viewModel.isLoading))
        assertTrue(getValue(viewModel.favouriteLocationsList).isEmpty())
        assertNull(getValue(viewModel.forecast))
        assertNull(getValue(viewModel.searchedLocations))
    }
}