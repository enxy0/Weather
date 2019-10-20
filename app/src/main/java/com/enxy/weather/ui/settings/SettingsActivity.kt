package com.enxy.weather.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.enxy.weather.R

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.d("SettingsActivity", "onSharedPreferenceChanged: ")
        when (key) {
            KEY_TEMPERATURE -> {
                val temperatureValue = sharedPreferences!!.getString(key, VALUE_DEFAULT_TEMPERATURE)
                // some work
            }
            KEY_WIND -> {
                val temperatureValue = sharedPreferences!!.getString(key, VALUE_DEFAULT_WIND)
                // some work
            }
            KEY_PRESSURE -> {
                val temperatureValue = sharedPreferences!!.getString(key, VALUE_DEFAULT_PRESSURE)
                // some work
            }
        }
    }

    companion object {
        const val KEY_TEMPERATURE = "temperature"
        const val VALUE_DEFAULT_TEMPERATURE = "celcius"
        const val KEY_WIND = "wind"
        const val VALUE_DEFAULT_WIND = "mps"
        const val KEY_PRESSURE = "pressure"
        const val VALUE_DEFAULT_PRESSURE = "millimeters"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsContainer, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        Log.d("SettingsActivity", "onResume: ")
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

}