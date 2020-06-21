package com.enxy.weather.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.enxy.weather.R
import com.enxy.weather.data.entity.Location
import com.enxy.weather.ui.WeatherViewModel
import com.enxy.weather.ui.favourite.FavouriteAdapter.FavouriteLocationListener
import com.enxy.weather.ui.settings.SettingsFragment
import com.enxy.weather.utils.exception.Failure
import com.enxy.weather.utils.extension.failure
import com.enxy.weather.utils.extension.observe
import com.enxy.weather.utils.extension.runDelayed
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.favourite_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class FavouriteFragment : BottomSheetDialogFragment(), FavouriteLocationListener {
    private val favouriteAdapter: FavouriteAdapter by inject { parametersOf(this) }
    private val viewModel: WeatherViewModel by sharedViewModel()

    override fun getTheme(): Int = R.style.CustomStyle_BottomSheetDialog

    companion object {
        const val TAG = "FavouriteFragment"
        private const val SETTINGS_RIPPLE_DELAY = 100L
        private const val FORECAST_RIPPLE_DELAY = 150L
        fun newInstance() = FavouriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favourite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        favouriteList.apply {
            adapter = favouriteAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            setHasFixedSize(true)
        }
        with(viewModel) {
            observe(favouriteLocationsList, ::renderData)
            failure(favouriteLocationsFailure, ::handleFailure)
        }
    }

    private fun renderData(favouriteLocations: ArrayList<Location>?) {
        favouriteLocations?.let {
            favouriteAdapter.updateData(favouriteLocations)
        }
    }

    private fun handleFailure(failure: Failure?) {
        failure?.let {
            // TODO: Add image for error or when there is no data
        }
    }

    private fun setUpListeners() {
        settings.setOnClickListener {
            runDelayed(SETTINGS_RIPPLE_DELAY) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, SettingsFragment.newInstance())
                    .addToBackStack(SettingsFragment.TAG)
                    .commit()
                dismiss()
            }
        }
    }

    override fun onLocationClick(location: Location) {
        viewModel.fetchWeatherForecast(location)
        runDelayed(FORECAST_RIPPLE_DELAY) {
            dismiss()
        }
    }
}