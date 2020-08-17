package com.enxy.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
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

    /**
     * If app is launched for the first time, then navigates to [SearchFragment] to pick the location.
     *
     * If not - opens [WeatherFragment].
     */
    private fun showNextScreen(isAppFirstLaunched: Boolean) {
        val fragment: BaseFragment = if (isAppFirstLaunched) {
            SearchFragment.newInstance()
        } else {
            WeatherFragment.newInstance()
        }
        supportFragmentManager.commitNow {
            replace(R.id.mainContainer, fragment)
        }
    }
}