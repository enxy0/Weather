package com.enxy.weather.ui

import android.os.Bundle
import androidx.fragment.app.commitNow
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.enxy.weather.R
import com.enxy.weather.base.BaseActivity
import com.enxy.weather.ui.main.MainFragment
import com.enxy.weather.ui.search.SearchFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        viewModel = getViewModel(this, viewModelFactory)
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
