package com.enxy.weather.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.entity.CurrentForecast
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.ui.MainActivity
import com.enxy.weather.ui.MainViewModel
import com.enxy.weather.ui.favourite.FavouriteFragment
import com.enxy.weather.ui.search.SearchFragment
import com.enxy.weather.utils.Pressure
import com.enxy.weather.utils.Wind
import com.enxy.weather.utils.exception.Failure
import com.enxy.weather.utils.extension.dpToPixels
import com.enxy.weather.utils.extension.failure
import com.enxy.weather.utils.extension.observe
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MainFragment : BaseFragment() {
    override val layoutId = R.layout.main_fragment
    private val viewModel: MainViewModel by sharedViewModel()
    private val hourAdapter: HourAdapter by inject()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBottomAppBar()
        setUpSwipeRefreshLayout()
        setUpRecyclerView()
        setHasOptionsMenu(true)
        favouriteToggle.setOnClickListener {
            viewModel.changeForecastFavouriteStatus(favouriteToggle.isChecked)
        }
        with(viewModel) {
            observe(forecast, ::renderForecast)
            observe(settings, ::renderCorrectUnits)
            observe(isLoading, ::showLoading)
            failure(forecastFailure, ::handleFailure)
        }
    }

    private fun setUpBottomAppBar() {
        (activity as MainActivity).setSupportActionBar(bottomAppBar)

        bottomAppBar.setNavigationOnClickListener {
            val fragment = FavouriteFragment.newInstance()
            fragment.show(parentFragmentManager, FavouriteFragment.TAG)
        }

        bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search_action -> {
                    parentFragmentManager.commit {
                        replace(R.id.mainContainer, SearchFragment.newInstance())
                        addToBackStack(SearchFragment.TAG)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    private fun setUpSwipeRefreshLayout() {
        swipeRefreshLayout.setProgressViewOffset(true, 0, 55.dpToPixels)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateWeatherForecast()
        }
    }

    private fun handleFailure(failure: Failure?) {
        failure?.let {
            Log.d("MainFragment", "handleFailure: Failure=${failure.javaClass.name}")
        }
    }

    private fun showLoading(isLoading: Boolean?) {
        isLoading?.let {
            swipeRefreshLayout.isRefreshing = isLoading
        }
    }

    private fun renderForecast(forecast: Forecast?) {
        forecast?.let {
            Log.d("MainFragment", "renderHourWeather: hourForecast=$it")
            renderCurrentForecast(it.currentForecast)
            hourAdapter.updateData(it.hourForecast.hourArrayList)
            if (mainContentLinearLayout.isInvisible)
                mainContentLinearLayout.isVisible = true
            favouriteToggle.isChecked = it.isFavourite
        }
    }

    private fun renderCurrentForecast(currentForecast: CurrentForecast) {
        Log.d("MainFragment", "renderCurrentWeather: currentForecast=$currentForecast")
        currentDescriptionTextView.text = currentForecast.description
        currentDescriptionImageView.setImageResource(currentForecast.imageId)
        currentTemperatureTextView.text = currentForecast.temperature
        currentFeelsLikeTextView.text = currentForecast.feelsLikeTemperature
        locationNameTextView.text = currentForecast.locationName
        currentHumidityValueTextView.text = currentForecast.humidity
        currentWindValueTextView.text = currentForecast.wind
        currentPressureValueTextView.text = currentForecast.pressure
    }

    private fun renderCorrectUnits(settings: AppSettings?) {
        settings?.let {
            when (settings.windUnit) {
                Wind.METERS_PER_SECOND ->
                    currentWindUnitTextView.setText(R.string.wind_value_meters_per_second)
                Wind.KILOMETERS_PER_HOUR ->
                    currentWindUnitTextView.setText(R.string.wind_value_kilometers_per_hour)
            }
            when (settings.pressureUnit) {
                Pressure.MILLIMETERS_OF_MERCURY ->
                    currentPressureUnitTextView.setText(R.string.pressure_value_millimeters)
                Pressure.HECTO_PASCALS ->
                    currentPressureUnitTextView.setText(R.string.pressure_value_pascals)
            }
        }
    }

    private fun setUpRecyclerView() {
        hourRecyclerView.apply {
            adapter = hourAdapter
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            isNestedScrollingEnabled = false
        }
    }
}
