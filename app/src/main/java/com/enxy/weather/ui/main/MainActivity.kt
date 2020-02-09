package com.enxy.weather.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.enxy.weather.AndroidApplication
import com.enxy.weather.R
import com.enxy.weather.ui.search.SearchFragment
import kotlinx.android.synthetic.main.main_activity.*
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
        setSupportActionBar(bottomAppBar)
        setUpListeners()
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

    private fun setUpListeners() {
        bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search_action -> {
                    supportFragmentManager.commit {
                        replace(R.id.mainContainer, SearchFragment.newInstance())
                        addToBackStack(SearchFragment.TAG)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}
