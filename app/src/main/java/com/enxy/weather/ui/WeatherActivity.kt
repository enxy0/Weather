package com.enxy.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.ui.search.SearchFragment
import com.enxy.weather.ui.weather.WeatherFragment
import com.enxy.weather.utils.extension.observe
import org.koin.android.ext.android.inject

class WeatherActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null)
            observe(viewModel.isAppFirstLaunched, ::showNextScreen)
    }

    private fun showNextScreen(isAppFirstLaunched: Boolean?) {
        isAppFirstLaunched?.let {
            val fragment: BaseFragment = if (isAppFirstLaunched)
                SearchFragment.newInstance()
            else
                WeatherFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .commitNow()
        }
    }
}