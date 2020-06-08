package com.enxy.weather.ui.settings

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.enxy.weather.BuildConfig
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.ui.MainViewModel
import com.enxy.weather.utils.Pressure
import com.enxy.weather.utils.Temperature
import com.enxy.weather.utils.Wind
import com.enxy.weather.utils.extension.observe
import com.enxy.weather.utils.extension.openLink
import kotlinx.android.synthetic.main.settings_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SettingsFragment : BaseFragment() {
    override val layoutId = R.layout.settings_fragment
    private val viewModel: SettingsViewModel by inject()
    private val activityViewModel: MainViewModel by sharedViewModel()

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance() = SettingsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAboutSection()
        with(viewModel) {
            // Units selected by user (or by default)
            observe(selectedTemperature, { temperature.text = it?.displayedName })
            observe(selectedWind, { wind.text = it?.displayedName })
            observe(selectedPressure, { pressure.text = it?.displayedName })
            // Lists of all available units
            observe(availableTemperatureUnits, ::setAvailableTemperatureUnits)
            observe(availableWindUnits, ::setAvailableWindUnits)
            observe(availablePressureUnits, ::setAvailablePressureUnits)
        }
    }

    private fun setAvailableTemperatureUnits(temperatureUnits: Array<Temperature>?) {
        temperatureUnits?.let {
            temperatureLayout.setOnClickListener {
                MaterialDialog(requireContext()).show {
                    title(R.string.temperature_title)
                    listItems(items = temperatureUnits.map { it.displayedName }) { _, index, text ->
                        this@SettingsFragment.temperature.text = text
                        viewModel.updateTemperatureUnit(temperatureUnits[index])
                        activityViewModel.updateForecastUnits()
                    }
                }
            }
        }
    }

    private fun setAvailableWindUnits(windUnits: Array<Wind>?) {
        windUnits?.let {
            windLayout.setOnClickListener {
                MaterialDialog(requireContext()).show {
                    title(R.string.wind_title)
                    listItems(items = windUnits.map { it.displayedName }) { _, index, text ->
                        this@SettingsFragment.wind.text = text
                        viewModel.updateWindUnit(windUnits[index])
                        activityViewModel.updateForecastUnits()
                    }
                }
            }
        }
    }

    private fun setAvailablePressureUnits(pressureUnits: Array<Pressure>?) {
        pressureUnits?.let {
            pressureLayout.setOnClickListener {
                MaterialDialog(requireContext()).show {
                    title(R.string.pressure_title)
                    listItems(items = pressureUnits.map { it.displayedName }) { _, index, text ->
                        this@SettingsFragment.pressure.text = text
                        viewModel.updatePressureUnit(pressureUnits[index])
                        activityViewModel.updateForecastUnits()
                    }
                }
            }
        }
    }

    private fun setUpAboutSection() {
        // About section
        githubLayout.setOnClickListener {
            openLink(R.string.settings_summary_github)
        }
        authorLayout.setOnClickListener {
            openLink(R.string.settings_vk_link)
        }
        appVersion.text = BuildConfig.VERSION_NAME
        buildNumber.text = BuildConfig.VERSION_CODE.toString()
    }
}