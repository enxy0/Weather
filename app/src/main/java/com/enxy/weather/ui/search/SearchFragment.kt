package com.enxy.weather.ui.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.commitNow
import androidx.recyclerview.widget.LinearLayoutManager
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.data.entity.LocationInfo
import com.enxy.weather.exception.Failure
import com.enxy.weather.ui.MainViewModel
import com.enxy.weather.ui.main.MainFragment
import com.enxy.weather.utils.extension.failure
import com.enxy.weather.utils.extension.observe
import kotlinx.android.synthetic.main.search_fragment.*


class SearchFragment : BaseFragment(), LocationAdapter.LocationListener {
    override val layoutId = R.layout.search_fragment
    private lateinit var viewModel: MainViewModel
    private lateinit var locationAdapter: LocationAdapter

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getMainViewModel()
        locationAdapter = LocationAdapter(this)
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
        if (isOpenedFirst())
            openMainFragment()
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

    private fun isOpenedFirst(): Boolean = parentFragmentManager.backStackEntryCount == 0

    private fun openMainFragment() {
        requireActivity().supportFragmentManager.commitNow {
            replace(R.id.mainContainer, MainFragment.newInstance())
        }
    }
}