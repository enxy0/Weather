package com.enxy.weather.di

import com.enxy.weather.network.NetworkService
import org.koin.dsl.module

val networkModule = module {
    single { NetworkService() }
}