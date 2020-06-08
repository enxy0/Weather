package com.enxy.weather.di

import com.enxy.weather.ui.favourite.FavouriteAdapter
import com.enxy.weather.ui.favourite.FavouriteAdapter.FavouriteLocationListener
import com.enxy.weather.ui.main.DayAdapter
import com.enxy.weather.ui.main.HourAdapter
import com.enxy.weather.ui.search.LocationAdapter
import com.enxy.weather.ui.search.LocationAdapter.LocationListener
import org.koin.dsl.module

val uiModule = module {
    factory { HourAdapter() }
    factory { DayAdapter() }
    factory { (listener: LocationListener) -> LocationAdapter(listener) }
    factory { (listener: FavouriteLocationListener) -> FavouriteAdapter(listener) }
}