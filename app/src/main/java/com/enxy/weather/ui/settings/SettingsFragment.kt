package com.enxy.weather.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
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
        appVersion.text = BuildConfig.VERSION_NAME
        buildNumber.text = BuildConfig.VERSION_CODE.toString()
        val openGithub =
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.settings_summary_github)))
        val openVk = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.settings_vk_link)))
        githubLayout.setOnClickListener { (activity as MainActivity).startActivity(openGithub) }
        authorLayout.setOnClickListener { (activity as MainActivity).startActivity(openVk) }
    }
}