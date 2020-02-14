package com.enxy.weather.ui.settings

import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment

class SettingsFragment : BaseFragment() {
    override val layoutId = R.layout.settings_fragment

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance() = SettingsFragment()
    }
}