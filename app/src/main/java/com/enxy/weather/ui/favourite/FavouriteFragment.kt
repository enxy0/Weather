package com.enxy.weather.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.enxy.weather.R
import com.enxy.weather.data.entity.LocationInfo
import com.enxy.weather.ui.MainViewModel
import com.enxy.weather.ui.favourite.FavouriteAdapter.FavouriteLocationListener
import com.enxy.weather.ui.settings.SettingsFragment
import com.enxy.weather.utils.exception.Failure
import com.enxy.weather.utils.extension.failure
import com.enxy.weather.utils.extension.observe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.favourite_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class FavouriteFragment : BottomSheetDialogFragment(), FavouriteLocationListener {
    private val favouriteAdapter: FavouriteAdapter by inject { parametersOf(this) }
    private val viewModel: MainViewModel by sharedViewModel()

    override fun getTheme(): Int = R.style.CustomStyle_BottomSheetDialog

    companion object {
        const val TAG = "FavouriteFragment"
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
        with(favouriteRecyclerView) {
            adapter = favouriteAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
        with(viewModel) {
            observe(favouriteLocationsList, ::renderData)
            failure(favouriteLocationsFailure, ::handleFailure)
        }
    }

    private fun renderData(favouriteLocationList: ArrayList<LocationInfo>?) {
        favouriteLocationList?.let { favouriteAdapter.updateData(it) }
    }

    private fun handleFailure(failure: Failure?) {
        failure?.let {
            // TODO: Add image for error or when there is no data
        }
    }

    private fun setUpListeners() {
        settingsButton.setOnClickListener {
            lifecycleScope.launch {
                delay(100) // 0.1 sec delay for showing ripple on the button
                parentFragmentManager.commit {
                    replace(R.id.mainContainer, SettingsFragment.newInstance())
                    addToBackStack(SettingsFragment.TAG)
                    this@FavouriteFragment.dismiss()
                }
            }
        }
    }

    override fun onLocationClick(locationInfo: LocationInfo) {
        lifecycleScope.launch {
            // TODO: Rewrite as a function
            viewModel.fetchWeatherForecast(locationInfo)
            delay(150) // 0.15 sec delay for showing ripple on the location
            this@FavouriteFragment.dismiss()
        }
    }
}