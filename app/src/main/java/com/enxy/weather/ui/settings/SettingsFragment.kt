package com.enxy.weather.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.enxy.weather.BuildConfig
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.ui.MainActivity
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : BaseFragment() {
    override val layoutId = R.layout.settings_fragment

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance() = SettingsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpListeners()
    }

    private fun setUpViews() {
        appVersion.text = BuildConfig.VERSION_NAME
        buildNumber.text = BuildConfig.VERSION_CODE.toString()
    }

    private fun setUpListeners() {
        // Units section
        temperatureLayout.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(R.string.temperature_title)
                listItems(R.array.temperature_entries)
            }
        }
        windLayout.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(R.string.wind_title)
                listItems(R.array.wind_entries)
            }
        }
        pressureLayout.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(R.string.pressure_title)
                listItems(R.array.pressure_entries)
            }
        }

        // About section
        githubLayout.setOnClickListener {
            val openGithub =
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.settings_summary_github)))
            (activity as MainActivity).startActivity(openGithub)
        }
        authorLayout.setOnClickListener {
            val openVk = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.settings_vk_link)))
            (activity as MainActivity).startActivity(openVk)
        }
    }
}