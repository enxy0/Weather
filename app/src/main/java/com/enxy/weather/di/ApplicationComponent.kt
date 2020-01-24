package com.enxy.weather.di

import com.enxy.weather.AndroidApplication
import com.enxy.weather.di.viewmodel.ViewModelModule
import com.enxy.weather.ui.main.MainActivity
import com.enxy.weather.ui.main.MainFragment
import com.enxy.weather.ui.search.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(application: AndroidApplication)
}