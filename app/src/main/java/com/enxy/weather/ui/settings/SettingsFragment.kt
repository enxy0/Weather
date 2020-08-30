package com.enxy.weather.ui.settings

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.enxy.weather.BuildConfig
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.ui.WeatherViewModel
import com.enxy.weather.utils.extension.openLink
import kotlinx.android.synthetic.main.settings_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SettingsFragment : BaseFragment() {
    override val layoutId = R.layout.settings_fragment
    private val viewModel: SettingsViewModel by inject()
    private val activityViewModel: WeatherViewModel by sharedViewModel()

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance() = SettingsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAppearanceSection()
        setUpUnitsSection()
        setUpAboutSection()
    }

    private fun setUpAppearanceSection() {
        theme.text = viewModel.theme
        appearanceLayout.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(R.string.theme_title)
                listItems(items = viewModel.availableThemes) { _, index, text ->
                    this@SettingsFragment.theme.text = text
                    viewModel.updateTheme(index)
                    activityViewModel.applyNewUnits()
                }
            }
        }
    }

    private fun setUpUnitsSection() {
        // Temperature
        temperature.text = viewModel.temperature
        temperatureLayout.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(R.string.temperature_title)
                listItems(items = viewModel.availableTemperatureUnits) { _, index, text ->
                    this@SettingsFragment.temperature.text = text
                    viewModel.updateTemperatureUnit(index)
                    activityViewModel.applyNewUnits()
                }
            }
        }

        // Wind
        wind.text = viewModel.wind
        windLayout.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(R.string.wind_title)
                listItems(items = viewModel.availableWindUnits) { _, index, text ->
                    this@SettingsFragment.wind.text = text
                    viewModel.updateWindUnit(index)
                    activityViewModel.applyNewUnits()
                }
            }
        }

        // Pressure
        pressure.text = viewModel.pressure
        pressureLayout.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(R.string.pressure_title)
                listItems(items = viewModel.availablePressureUnits) { _, index, text ->
                    this@SettingsFragment.pressure.text = text
                    viewModel.updatePressureUnit(index)
                    activityViewModel.applyNewUnits()
                }
            }
        }
    }


    private fun setUpAboutSection() {
        githubLayout.setOnClickListener { openLink(R.string.settings_summary_github) }
        authorLayout.setOnClickListener { openLink(R.string.settings_vk_link) }
        appVersion.text = BuildConfig.VERSION_NAME
        buildNumber.text = BuildConfig.VERSION_CODE.toString()
    }
}