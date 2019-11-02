package com.enxy.weather.di

import com.enxy.weather.AndroidApplication
import com.enxy.weather.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(application: AndroidApplication)
}