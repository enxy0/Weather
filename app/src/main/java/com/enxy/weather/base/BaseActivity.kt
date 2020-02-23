package com.enxy.weather.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.enxy.weather.AndroidApplication
import com.enxy.weather.di.ApplicationComponent
import com.enxy.weather.ui.MainViewModel

abstract class BaseActivity : AppCompatActivity() {
    val appComponent: ApplicationComponent by lazy {
        (application as AndroidApplication).appComponent
    }

    fun getViewModel(activity: BaseActivity, factory: ViewModelProvider.Factory) =
        ViewModelProvider(activity, factory).get(MainViewModel::class.java)
}