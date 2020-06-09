package com.enxy.weather.ui.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.*
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.data.entity.Location
import com.enxy.weather.ui.MainViewModel
import com.enxy.weather.ui.main.MainFragment
import com.enxy.weather.ui.search.LocationAdapter.LocationListener
import com.enxy.weather.utils.exception.Failure
import com.enxy.weather.utils.extension.failure
import com.enxy.weather.utils.extension.observe
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf


class SearchFragment : BaseFragment(), LocationListener {
    override val layoutId = R.layout.search_fragment
    private val viewModel: MainViewModel by sharedViewModel()
    private val locationAdapter: LocationAdapter by inject { parametersOf(this) }

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setFocusOnInput()
        showHintIfNeeded()
        searchCityEditText.doOnTextChanged { text, _, _, _ ->
            text?.let { if (it.length > 1) viewModel.fetchListOfLocationsByName(it.toString()) }
        }
        with(viewModel) {
            observe(searchedLocations, ::renderData)
            failure(searchedLocationsFailure, ::handleFailure)
        }
    }

    private fun renderData(locations: ArrayList<Location>?) {
        locations?.let {
            if (hint.isVisible) {
                hint.isGone = true
                locationList.isVisible = true
            }
            locationAdapter.updateData(locations)
        }
    }

    private fun handleFailure(failure: Failure?) {
        failure?.let { notify("Failure: ${it.javaClass.simpleName}") }
    }

    override fun onLocationChange(location: Location) {
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

    private fun showHintIfNeeded() {
        if (locationAdapter.itemCount == 0) {
            hint.isVisible = true
            locationList.isGone = true
        }
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
            .replace(R.id.mainContainer, MainFragment.newInstance())
            .commitNow()
    }
}