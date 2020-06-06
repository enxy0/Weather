package com.enxy.weather.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.enxy.weather.BuildConfig
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.extension.observe
import com.enxy.weather.ui.MainActivity
import com.enxy.weather.ui.MainViewModel
import com.enxy.weather.utils.Pressure
import com.enxy.weather.utils.Temperature
import com.enxy.weather.utils.Wind
import kotlinx.android.synthetic.main.settings_fragment.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {
    override val layoutId = R.layout.settings_fragment
    lateinit var viewModel: SettingsViewModel
    lateinit var activityViewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance() = SettingsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]
        activityViewModel = getMainViewModel()
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
        setUpViews()
        setUpListeners()
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

    private fun setUpViews() {
        appVersion.text = BuildConfig.VERSION_NAME
        buildNumber.text = BuildConfig.VERSION_CODE.toString()
    }

    private fun setUpListeners() {
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