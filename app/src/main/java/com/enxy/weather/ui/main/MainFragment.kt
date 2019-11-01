package com.enxy.weather.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.exception.Failure
import com.enxy.weather.extension.failure
import com.enxy.weather.extension.observe
import com.enxy.weather.model.CurrentWeatherModel
import com.enxy.weather.model.HourWeatherModel
import com.enxy.weather.ui.main.adapter.DayAdapter
import com.enxy.weather.ui.main.adapter.HourAdapter
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : BaseFragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    override val layoutId = R.layout.main_fragment
    private lateinit var viewModel: MainViewModel
    private val hourAdapter = HourAdapter()
    private val dayAdapter = DayAdapter()
    private fun mainActivity() = (activity as MainActivity)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setUpRecyclerView()
        with(viewModel) {
            observe(currentWeatherModel, ::renderCurrentWeather)
            failure(currentWeatherFailure, ::handleFailure)
            observe(hourWeatherModelArrayList, ::renderHourWeather)
            failure(hourWeatherFailure, ::handleFailure)
        }
    }

    private fun handleFailure(failure: Failure?) {
        failure?.let {
            Log.d("MainFragment", "handleFailure: Failure=${failure.javaClass.name}")
        }
    }

    private fun renderHourWeather(hourWeatherModelArrayList: ArrayList<HourWeatherModel>?) {
        hourWeatherModelArrayList?.let {
            hourAdapter.updateData(it)
        }
    }

    private fun renderCurrentWeather(currentWeatherModel: CurrentWeatherModel?) {
        currentWeatherModel?.let {
            currentDescriptionTextView.text = currentWeatherModel.description
            currentDescriptionImageView.setImageResource(currentWeatherModel.imageId)
            currentTemperatureTextView.text = currentWeatherModel.temperature
            currentFeelsLikeTextView.text = currentWeatherModel.feelsLikeTemperature
            cityNameTextView.text = currentWeatherModel.cityName
            currentHumidityValueTextView.text = currentWeatherModel.humidity
            currentWindValueTextView.text = currentWeatherModel.wind
            currentPressureValueTextView.text = currentWeatherModel.pressure

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
