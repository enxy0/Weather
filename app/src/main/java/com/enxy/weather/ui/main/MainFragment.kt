package com.enxy.weather.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.exception.Failure
import com.enxy.weather.extension.dpToPixels
import com.enxy.weather.extension.failure
import com.enxy.weather.extension.observe
import com.enxy.weather.ui.main.adapter.DayAdapter
import com.enxy.weather.ui.main.adapter.HourAdapter
import com.enxy.weather.ui.main.model.CurrentWeatherModel
import com.enxy.weather.ui.main.model.HourWeatherModel
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject


class MainFragment : BaseFragment() {
    override val layoutId = R.layout.main_fragment
    private lateinit var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var hourAdapter: HourAdapter
    @Inject
    lateinit var dayAdapter: DayAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        viewModel = getMainViewModel(viewModelFactory)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSwipeRefreshLayout()
        setUpRecyclerView()
        with(viewModel) {
            observe(currentWeatherModel, ::renderCurrentWeather)
            failure(currentWeatherFailure, ::handleFailure)
            observe(hourWeatherModelArrayList, ::renderHourWeather)
            failure(hourWeatherFailure, ::handleFailure)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.let {
            it.currentWeatherModel.removeObservers(this)
            it.currentWeatherFailure.removeObservers(this)
            it.hourWeatherModelArrayList.removeObservers(this)
            it.hourWeatherFailure.removeObservers(this)
        }
    }

    private fun setUpSwipeRefreshLayout() {
        swipeRefreshLayout.setProgressViewOffset(true, 0, 55.dpToPixels)
        swipeRefreshLayout.setOnRefreshListener(::onRefresh)
    }

    private fun onRefresh() {
        if (!swipeRefreshLayout.isRefreshing)
            swipeRefreshLayout.isRefreshing = true
        viewModel.fetchWeatherForecast()
    }

    private fun handleFailure(failure: Failure?) {
        failure?.let {
            Log.d("MainFragment", "handleFailure: Failure=${failure.javaClass.name}")
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun renderHourWeather(hourWeatherModelArrayList: ArrayList<HourWeatherModel>?) {
        hourWeatherModelArrayList?.let {
            hourAdapter.updateData(it)
            Log.d("MainFragment", "renderHourWeather: hourWeatherModelArrayList=$it")
            mainContentLinearLayout.isVisible = true
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun renderCurrentWeather(currentWeatherModel: CurrentWeatherModel?) {
        currentWeatherModel?.let {
            Log.d("MainFragment", "renderCurrentWeather: currentWeatherModel=$it")
            currentDescriptionTextView.text = currentWeatherModel.description
            currentDescriptionImageView.setImageResource(currentWeatherModel.imageId)
            currentTemperatureTextView.text = currentWeatherModel.temperature
            currentFeelsLikeTextView.text = currentWeatherModel.feelsLikeTemperature
            cityNameTextView.text = currentWeatherModel.cityName
            currentHumidityValueTextView.text = currentWeatherModel.humidity
            currentWindValueTextView.text = currentWeatherModel.wind
            currentPressureValueTextView.text = currentWeatherModel.pressure
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setUpRecyclerView() {
        hourRecyclerView.adapter = hourAdapter

        hourRecyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
        hourRecyclerView.isNestedScrollingEnabled = false
    }
}
