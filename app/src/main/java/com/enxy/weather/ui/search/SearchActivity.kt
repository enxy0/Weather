package com.enxy.weather.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enxy.weather.R

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.searchContainer, SearchFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}