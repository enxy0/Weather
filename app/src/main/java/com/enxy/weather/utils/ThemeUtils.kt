package com.enxy.weather.utils

import androidx.appcompat.app.AppCompatDelegate
import com.enxy.weather.utils.Theme.LIGHT
import com.enxy.weather.utils.Theme.NIGHT

class ThemeUtils {
    companion object {
        fun setAppTheme(theme: Theme) {
            when (theme) {
                LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                NIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}