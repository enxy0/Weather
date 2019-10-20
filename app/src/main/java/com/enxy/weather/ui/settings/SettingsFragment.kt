package com.enxy.weather.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.enxy.weather.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

}