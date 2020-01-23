package com.enxy.weather.ui.search

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.enxy.weather.R
import com.enxy.weather.base.BaseFragment
import com.enxy.weather.exception.Failure
import com.enxy.weather.extension.failure
import com.enxy.weather.extension.observe
import com.enxy.weather.network.NetworkService
import com.enxy.weather.ui.main.MainViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject


class SearchFragment : BaseFragment() {
    override val layoutId = R.layout.search_fragment
    @Inject
    lateinit var networkService: NetworkService
    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val locationAdapter = LocationAdapter()

    companion object {
        const val TAG = "SearchFragment"
        fun newInstance() = SearchFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        appComponent.inject(this)
        viewModel = getMainViewModel(viewModelFactory)
        setUpRecyclerView()
        setFocusOnInput()
        with(viewModel) {
            observe(locationInfoArrayList, ::renderData)
            failure(locationFailure, ::handleFailure)
        }
        searchCityEditText.doOnTextChanged { text, _, _, _ ->
            text?.let { if (it.length > 1) viewModel.fetchListOfLocationsByName(it.toString()) }
        }
    }

    private fun renderData(locationInfoArrayList: ArrayList<LocationInfo>?) {
        locationInfoArrayList?.let { locationAdapter.updateData(it) }
    }

    private fun handleFailure(failure: Failure?) {
        failure?.let { notify("Failure: ${it.javaClass.simpleName}") }
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
            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}