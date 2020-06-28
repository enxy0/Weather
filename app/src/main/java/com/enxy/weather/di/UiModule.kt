package com.enxy.weather.di

import com.enxy.weather.data.entity.Location
import com.enxy.weather.ui.search.LocationAdapter
import com.enxy.weather.ui.weather.DayAdapter
import com.enxy.weather.ui.weather.HourAdapter
import org.koin.dsl.module

val uiModule = module {
    factory { HourAdapter() }
    factory { DayAdapter() }
    factory { (onLocationChange: (Location) -> Unit) -> LocationAdapter(onLocationChange) }
}