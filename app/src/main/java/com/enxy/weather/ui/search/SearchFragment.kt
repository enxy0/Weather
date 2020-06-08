package com.enxy.weather.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.data.entity.LocationInfo
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
        showHint()
        searchCityEditText.doOnTextChanged { text, _, _, _ ->
            text?.let { if (it.length > 1) viewModel.fetchListOfLocationsByName(it.toString()) }
        }
        with(viewModel) {
            observe(locationInfoArrayList, ::renderData)
            failure(locationFailure, ::handleFailure)
        }
    }

    private fun renderData(locationInfoArrayList: ArrayList<LocationInfo>?) {
        locationInfoArrayList?.let {
            if (hint.isVisible) {
                hint.isGone = true
                locationRecyclerView.isVisible = true
            }
            locationAdapter.updateData(it)
        }
    }

    private fun handleFailure(failure: Failure?) {
        failure?.let { notify("Failure: ${it.javaClass.simpleName}") }
    }

    override fun onLocationChange(locationInfo: LocationInfo) {
        viewModel.fetchWeatherForecast(locationInfo)
        hideKeyboard()
        if (isAppFirstLaunched())
            showMainScreen()
        else
            parentFragmentManager.popBackStack()
    }

    private fun setUpRecyclerView() {
        locationRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        locationRecyclerView.adapter = locationAdapter
        locationRecyclerView.setHasFixedSize(true)
    }

    private fun setFocusOnInput() {
        searchCityEditText.requestFocus()
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    private fun showHint() {
        if (locationAdapter.itemCount == 0) {
            hint.isVisible = true
            locationRecyclerView.isGone = true
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            searchCityEditText.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
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