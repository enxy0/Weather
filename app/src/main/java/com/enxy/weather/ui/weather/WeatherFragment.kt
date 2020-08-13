package com.enxy.weather.ui.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.entity.CurrentForecast
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.ui.WeatherActivity
import com.enxy.weather.ui.WeatherViewModel
import com.enxy.weather.ui.favourite.FavouriteFragment
import com.enxy.weather.ui.search.SearchFragment
import com.enxy.weather.utils.PressureUnit.HECTO_PASCALS
import com.enxy.weather.utils.PressureUnit.MILLIMETERS_OF_MERCURY
import com.enxy.weather.utils.WindUnit.KILOMETERS_PER_HOUR
import com.enxy.weather.utils.WindUnit.METERS_PER_SECOND
import com.enxy.weather.utils.exception.BadServerResponse
import com.enxy.weather.utils.exception.NoConnection
import com.enxy.weather.utils.extension.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.precipitation_card_view.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class WeatherFragment : BaseFragment() {
    override val layoutId = R.layout.main_fragment
    private val viewModel: WeatherViewModel by sharedViewModel()
    private val hourAdapter: HourAdapter by inject()
    private val dayAdapter: DayAdapter by inject()

    companion object {
        fun newInstance() = WeatherFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            renderUnits(appSettings)
            observe(forecast, ::renderForecast)
            observe(forecastFailure, ::handleFailure)
            observe(isLoading, ::onLoading)
        }
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
    }

    /**
     * Handles with [forecast] data
     */
    private fun renderForecast(forecast: Forecast) {
        renderCurrentForecast(forecast.currentForecast)
        hourAdapter.submitList(forecast.hourForecastList)
        dayAdapter.submitList(forecast.dayForecastList)
        locationName.text = forecast.locationName
        favouriteToggle.isChecked = forecast.isFavourite
        if (mainContentLinearLayout.isInvisible) {
            mainContentLinearLayout.fadeIn()
        }
    }

    /**
     * Displays [currentForecast] data
     */
    private fun renderCurrentForecast(currentForecast: CurrentForecast) = with(currentForecast) {
        currentDescription.text = description
        currentDescriptionImage.animateScaleFadeChange(imageId)
        currentTemperature.animateNumberChange(newValue = temperature.value, isSignShown = true)
        currentFeelsLike.animateNumberChange(newValue = feelsLike.value, isSignShown = true)
        humidityCard.value.animateNumberChange(newValue = humidity)
        windCard.value.animateNumberChange(newValue = wind.value)
        pressureCard.value.animateNumberChange(newValue = pressure.value)
    }

    /**
     * Displays applied units from [AppSettings]
     */
    private fun renderUnits(settings: AppSettings) {
        when (settings.windUnit) {
            METERS_PER_SECOND ->
                windCard.unit.setText(R.string.wind_value_meters_per_second)
            KILOMETERS_PER_HOUR ->
                windCard.unit.setText(R.string.wind_value_kilometers_per_hour)
        }
        when (settings.pressureUnit) {
            MILLIMETERS_OF_MERCURY ->
                pressureCard.unit.setText(R.string.pressure_value_millimeters)
            HECTO_PASCALS ->
                pressureCard.unit.setText(R.string.pressure_value_pascals)
        }
    }

    /**
     * Handles with failures that may occur while loading data.
     *
     * Notifies user via SnackBar about them.
     */
    private fun handleFailure(failure: Exception?) {
        when (failure) {
            is NoConnection -> {
                snackbarAction(
                    coordinatorLayout,
                    bottomAppBar,
                    R.string.failure_connection_error,
                    R.string.button_try_again
                ) {
                    viewModel.updateForecast()
                    swipeRefreshLayout.isRefreshing = true
                }
            }
            is BadServerResponse -> {
                snackbarAction(
                    coordinatorLayout,
                    bottomAppBar,
                    R.string.failure_server_error,
                    R.string.button_try_again
                ) {
                    viewModel.updateForecast()
                    swipeRefreshLayout.isRefreshing = true
                }
            }
            else -> {
                // TODO: Handle other failures?
            }
        }
    }

    /**
     * Changes [swipeRefreshLayout] loading status when loading data
     */
    private fun onLoading(isLoading: Boolean) {
        swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun setUpBottomAppBar() {
        (activity as WeatherActivity).setSupportActionBar(bottomAppBar)

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
        swipeRefreshLayout.setProgressViewOffset(true, 0, 55.dp)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateForecast()
        }
    }

    private fun setUpRecyclerView() {
        hourRecyclerView.run {
            adapter = hourAdapter
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            isNestedScrollingEnabled = false
        }
        dayList.run {
            adapter = dayAdapter
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            isNestedScrollingEnabled = false
        }
    }
}
