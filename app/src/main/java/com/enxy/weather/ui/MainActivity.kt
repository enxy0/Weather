package com.enxy.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.enxy.weather.AndroidApplication
import com.enxy.weather.R
import com.enxy.weather.ui.main.MainFragment
import com.enxy.weather.ui.search.SearchFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as AndroidApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        setContentView(R.layout.main_activity)
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
