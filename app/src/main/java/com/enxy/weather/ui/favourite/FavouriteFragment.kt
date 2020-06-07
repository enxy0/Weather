package com.enxy.weather.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.enxy.weather.AndroidApplication
import com.enxy.weather.R
import com.enxy.weather.data.entity.LocationInfo
import com.enxy.weather.exception.Failure
import com.enxy.weather.ui.MainViewModel
import com.enxy.weather.ui.settings.SettingsFragment
import com.enxy.weather.utils.extension.failure
import com.enxy.weather.utils.extension.observe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.favourite_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteFragment : BottomSheetDialogFragment(), FavouriteAdapter.FavouriteLocationListener {
    private lateinit var favouriteAdapter: FavouriteAdapter
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

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
        inject()
        setUpListeners()
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)
        favouriteAdapter = FavouriteAdapter(this)
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
            viewModel.fetchWeatherForecast(locationInfo)
            delay(150) // 0.15 sec delay for showing ripple on the location
            this@FavouriteFragment.dismiss()
        }
    }

    private fun inject() {
        (requireActivity().application as AndroidApplication).appComponent.inject(this)
    }
}