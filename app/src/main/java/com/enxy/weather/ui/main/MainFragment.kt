package com.enxy.weather.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.exception.Failure
import com.enxy.weather.extension.dpToPixels
import com.enxy.weather.extension.failure
import com.enxy.weather.extension.observe
import com.enxy.weather.extension.startCircularRevealAnimation
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
    private var isFirstTimeCreated: Boolean = true

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        appComponent.inject(this)
        viewModel = getMainViewModel(viewModelFactory)
        setUpSwipeRefreshLayout()
        mainContentLinearLayout.isInvisible = true
        setUpRecyclerView()
        with(viewModel) {
            observe(currentWeatherModel, ::renderCurrentWeather)
            failure(currentWeatherFailure, ::handleFailure)
            observe(hourWeatherModelArrayList, ::renderHourWeather)
            failure(hourWeatherFailure, ::handleFailure)
        }
    }

    private fun setUpSwipeRefreshLayout() {
        swipeRefreshLayout.setProgressViewOffset(true, 0, 55.dpToPixels)
        swipeRefreshLayout.setOnRefreshListener { viewModel.updateWeatherForecast() }
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
            if (isFirstTimeCreated) {
                val listener = object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        mainContentLinearLayout.isVisible = true
                    }
                }
                mainContentLinearLayout.startCircularRevealAnimation(listener, 300, 180)
                isFirstTimeCreated = false
            }
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
