package com.enxy.weather.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.enxy.weather.R
import com.enxy.weather.data.entity.MiniForecast
import com.enxy.weather.ui.WeatherViewModel
import com.enxy.weather.ui.settings.SettingsFragment
import com.enxy.weather.utils.extension.dismiss
import com.enxy.weather.utils.extension.observe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.favourite_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class FavouriteFragment : BottomSheetDialogFragment() {
    private val viewModel: FavouriteViewModel by inject()
    private val activityViewModel: WeatherViewModel by sharedViewModel()
    private val favouriteAdapter: FavouriteAdapter by inject {
        parametersOf(::onForecastChange)
    }

    override fun getTheme(): Int = R.style.CustomStyle_BottomSheetDialog

    companion object {
        const val TAG = "FavouriteFragment"
        private const val ICON_RIPPLE_DELAY = 100L
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
            observe(favouriteLocations, ::renderData)
            observe(failure, ::handleFailure)
        }
    }

    private fun renderData(favouriteLocations: List<MiniForecast>) {
        favouriteAdapter.updateData(favouriteLocations)
    }

    private fun handleFailure(failure: Exception) {
        // TODO: Add image for error or when there is no data
    }

    private fun setUpListeners() {
        settings.setOnClickListener {
            dismiss(ICON_RIPPLE_DELAY) {
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                    .replace(R.id.mainContainer, SettingsFragment.newInstance())
                    .addToBackStack(SettingsFragment.TAG)
                    .commit()
            }
        }
        close.setOnClickListener {
            dismiss(delay = ICON_RIPPLE_DELAY)
        }
    }

    /**
     * Changes displayed (current) forecast and closes BottomSheet
     */
    private fun onForecastChange(miniForecast: MiniForecast) {
        activityViewModel.fetchForecast(miniForecast)
        // Delay changing screen to show ripple effect
        dismiss(delay = FORECAST_RIPPLE_DELAY)
    }
}