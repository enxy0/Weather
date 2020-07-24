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
import com.enxy.weather.utils.Pressure
import com.enxy.weather.utils.Wind
import com.enxy.weather.utils.exception.Failure
import com.enxy.weather.utils.exception.Failure.NoConnection
import com.enxy.weather.utils.exception.Failure.BadServerResponse
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
            observe(forecast, ::renderForecast)
            observe(settings, ::renderCorrectUnits)
            observe(isLoading, ::showLoading)
            failure(forecastFailure, ::handleFailure)
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
            viewModel.updateWeatherForecast()
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NoConnection -> {
                snackbarAction(
                    coordinatorLayout,
                    bottomAppBar,
                    R.string.failure_connection_error,
                    R.string.button_try_again
                ) {
                    viewModel.updateWeatherForecast()
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
                    viewModel.updateWeatherForecast()
                    swipeRefreshLayout.isRefreshing = true
                }
            }
            else -> {
                /* TODO: Handle other failures? */
            }
        }
    }

    private fun showLoading(isLoading: Boolean?) {
        isLoading?.let {
            swipeRefreshLayout.isRefreshing = isLoading
        }
    }

    private fun renderForecast(forecast: Forecast?) {
        forecast?.let {
            renderCurrentForecast(it.currentForecast)
            hourAdapter.updateData(it.hourForecastList)
            dayAdapter.updateData(it.dayForecastList)
            locationName.text = it.locationName
            favouriteToggle.isChecked = it.isFavourite
            if (mainContentLinearLayout.isInvisible)
                mainContentLinearLayout.show()
        }
    }

    private fun renderCurrentForecast(currentForecast: CurrentForecast) {
        currentDescription.text = currentForecast.description
        currentDescriptionImage.setImageResource(currentForecast.imageId)
        currentTemperature.text = currentForecast.temperature.withSign()
        currentFeelsLike.text = currentForecast.feelsLike.withSign()
        humidityCard.value.text = currentForecast.humidity.toString()
        windCard.value.text = currentForecast.wind.toString()
        pressureCard.value.text = currentForecast.pressure.toString()
    }

    private fun renderCorrectUnits(settings: AppSettings?) {
        settings?.let {
            when (settings.windUnit) {
                Wind.METERS_PER_SECOND ->
                    windCard.unit.setText(R.string.wind_value_meters_per_second)
                Wind.KILOMETERS_PER_HOUR ->
                    windCard.unit.setText(R.string.wind_value_kilometers_per_hour)
            }
            when (settings.pressureUnit) {
                Pressure.MILLIMETERS_OF_MERCURY ->
                    pressureCard.unit.setText(R.string.pressure_value_millimeters)
                Pressure.HECTO_PASCALS ->
                    pressureCard.unit.setText(R.string.pressure_value_pascals)
            }
        }
    }

    private fun setUpRecyclerView() {
        hourRecyclerView.apply {
            adapter = hourAdapter
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            isNestedScrollingEnabled = false
        }
        dayList.apply {
            adapter = dayAdapter
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            isNestedScrollingEnabled = false
        }
    }
}
