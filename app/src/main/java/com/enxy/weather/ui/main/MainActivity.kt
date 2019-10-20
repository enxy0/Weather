package com.enxy.weather.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enxy.weather.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, MainFragment.newInstance())
                .commitNow()
        }
    }
}
