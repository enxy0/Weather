package com.enxy.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.ui.main.MainFragment
import com.enxy.weather.ui.search.SearchFragment
import com.enxy.weather.utils.extension.observe
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by inject()

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
                MainFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .commitNow()
        }
    }
}