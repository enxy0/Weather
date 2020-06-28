package com.enxy.weather.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.*
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.data.entity.Location
import com.enxy.weather.ui.WeatherViewModel
import com.enxy.weather.ui.weather.WeatherFragment
import com.enxy.weather.utils.exception.Failure
import com.enxy.weather.utils.extension.*
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class SearchFragment : BaseFragment() {
    override val layoutId = R.layout.search_fragment
    private val viewModel: WeatherViewModel by sharedViewModel()
    private val locationAdapter: LocationAdapter by inject {
        parametersOf(::onLocationChange)
    }

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            observe(searchedLocations, ::renderData)
            failure(searchedLocationsFailure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setFocusOnInput()
        showHint(enterHint)
        searchCityEditText.doOnTextChanged { text, _, _, _ ->
            text?.let { if (it.length > 1) viewModel.fetchListOfLocationsByName(it.toString()) }
        }
    }

    private fun renderData(locations: ArrayList<Location>?) {
        locations?.let {
            if (locations.isEmpty()) {
                handleFailure(Failure.NoLocationsFound)
            } else {
                hints.hide()
                locationList.show()
                locationAdapter.updateData(locations)
            }
        }
    }

    private fun showHint(hintToShow: View) {
        locationList.hide()
        hints.show()
        hints.children.iterator().forEach {
            if (it != hintToShow)
                it.hide()
            else
                it.show()
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NoLocationsFound -> showHint(noLocationsHint)
            is Failure.ConnectionError -> showHint(noInternetHint)
            is Failure.ServerError -> snackbarShort(rootLayout, R.string.failure_server_error)
        }
    }

    private fun onLocationChange(location: Location) {
        viewModel.fetchWeatherForecast(location)
        hideKeyboard()
        if (isAppFirstLaunched())
            showMainScreen()
        else
            parentFragmentManager.popBackStack()
    }

    private fun setUpRecyclerView() {
        locationList.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            adapter = locationAdapter
            setHasFixedSize(true)
        }
    }

    private fun setFocusOnInput() {
        searchCityEditText.requestFocus()
        val inputMethodManager =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(SHOW_FORCED, HIDE_IMPLICIT_ONLY)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            searchCityEditText.windowToken,
            RESULT_UNCHANGED_SHOWN
        )
    }

    private fun isAppFirstLaunched(): Boolean = parentFragmentManager.backStackEntryCount == 0

    private fun showMainScreen() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, WeatherFragment.newInstance())
            .commitNow()
    }
}