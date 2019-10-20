package com.enxy.weather.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.extension.getDrawableByName
import com.enxy.weather.extension.observe
import com.enxy.weather.model.AllWeatherDataModel
import com.enxy.weather.model.CurrentDataModel
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
        with(viewModel) { observe(allWeatherDataModel, ::renderData) }
    }

    private fun setUpRecyclerView() {
        dayRecyclerView.adapter = dayAdapter
        hourRecyclerView.adapter = hourAdapter

        hourRecyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
        dayRecyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        dayRecyclerView.isNestedScrollingEnabled = false
        hourRecyclerView.isNestedScrollingEnabled = false
    }

    private fun renderData(allWeatherDataModel: AllWeatherDataModel?) {
        allWeatherDataModel?.let {
            hourAdapter.updateData(allWeatherDataModel.hourDataModelArrayList)
            dayAdapter.updateData(allWeatherDataModel.dayDataModelArrayList)
            setCurrentWeatherData(allWeatherDataModel.currentDataModel)
        }
    }

    private fun setCurrentWeatherData(currentDataModel: CurrentDataModel) {
        currentDescriptionTextView.text = currentDataModel.description
        setCurrentDescriptionImage(currentDataModel.image)
        setCurrentTemperature(currentDataModel.temperature)
        setCurrentFeelsLike(currentDataModel.temperature, currentDataModel.wind)
        setCurrentHumidity(currentDataModel.humidity)
        setCurrentWind(currentDataModel.wind)
        setCurrentPressure(currentDataModel.pressure)
    }

    private fun setCurrentDescriptionImage(imageName: String) {
        currentDescriptionImageView.setImageDrawable(context!!.getDrawableByName(imageName))
    }

    private fun setCurrentFeelsLike(temperature: Int, wind: Int) {
        val feelsLikeText: String = context!!.resources.getString(R.string.feels_like)
        val degreeSign = '°'
        // To imitate feels like work, which depends on many variables
        // I'll use "pseudo" algorithm: temperature - wind (it's easy and almost the same value xd)
        val calculatedFeelsLikeValue = temperature - wind
        var feelsLike = "$feelsLikeText "
        if (calculatedFeelsLikeValue > 0)
            feelsLike += "+"
        else if (calculatedFeelsLikeValue < 0)
            feelsLike += "-"
        feelsLike += calculatedFeelsLikeValue
        feelsLike += degreeSign
        currentFeelsLikeTextView.text = feelsLike
    }

    private fun setCurrentTemperature(temperature: Int) {
        val degreeSign = '°'
        var temperatureWithSign = ""
        if (temperature > 0)
            temperatureWithSign = "+"
        else if (temperature < 0)
            temperatureWithSign = "-"
        temperatureWithSign += temperature
        temperatureWithSign += degreeSign
        currentTemperatureTextView.text = temperatureWithSign
    }

    private fun setCurrentPressure(pressure: Int) {
        val pressureValue = context!!
            .resources
            .getString(R.string.pressure_value_pascals)
        val currentPressure = "$pressure $pressureValue"
        currentPressureTextView.text = currentPressure
    }

    private fun setCurrentWind(wind: Int) {
        val windValue = context!!
            .resources
            .getString(R.string.wind_value_meters_per_second)
        val currentWind = "$wind $windValue"
        currentWindTextView.text = currentWind
    }

    private fun setCurrentHumidity(humidity: Int) {
        val currentHumidity = "$humidity %"
        currentHumidityTextView.text = currentHumidity
    }
}
