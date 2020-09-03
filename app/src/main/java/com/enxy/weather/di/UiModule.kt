package com.enxy.weather.di

import com.enxy.weather.data.entity.FavouriteForecast
import com.enxy.weather.data.entity.Location
import com.enxy.weather.ui.favourite.FavouriteAdapter
import com.enxy.weather.ui.search.SearchAdapter
import com.enxy.weather.ui.weather.DailyForecastAdapter
import com.enxy.weather.ui.weather.HourlyForecastAdapter
import org.koin.dsl.module

val uiModule = module {
    factory { HourlyForecastAdapter() }
    factory { DailyForecastAdapter() }
    factory { (onLocationChange: (Location) -> Unit) -> SearchAdapter(onLocationChange) }
    factory { (onForecastChange: (FavouriteForecast) -> Unit) -> FavouriteAdapter(onForecastChange) }
}