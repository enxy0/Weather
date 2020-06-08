package com.enxy.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import androidx.lifecycle.lifecycleScope
import com.enxy.weather.R
import com.enxy.weather.ui.main.MainFragment
import com.enxy.weather.ui.search.SearchFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        showFragment(savedInstanceState)
    }

    private fun showFragment(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            if (savedInstanceState == null) {
                if (viewModel.isAppFirstLaunched())
                    supportFragmentManager.commitNow {
                        replace(R.id.mainContainer, SearchFragment.newInstance())
                    }
                else
                    supportFragmentManager.commitNow {
                        replace(R.id.mainContainer, MainFragment.newInstance())
                    }
            }
        }
    }
}
