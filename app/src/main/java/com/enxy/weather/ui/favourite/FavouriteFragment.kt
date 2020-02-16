package com.enxy.weather.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.enxy.weather.R
import com.enxy.weather.data.model.LocationInfo
import com.enxy.weather.ui.settings.SettingsFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.favourite_fragment.*

class FavouriteFragment : BottomSheetDialogFragment() {
    private val favouriteAdapter = FavouriteAdapter()

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
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
        // Test Data
        favouriteAdapter.updateData(
            arrayListOf(
                LocationInfo("Saint-Petersburg, Russia, 190000", 1.0, 1.0),
                LocationInfo("Moscow, Central Administrative Okrug, Russia", 1.0, 1.0),
                LocationInfo("Syktyvkar, Komi Republic, Russia, 167000", 1.0, 1.0),
                LocationInfo("Los Angeles, CA, United States of America", 1.0, 1.0)
            )
        )
    }

    private fun setUpListeners() {
        settingsButton.setOnClickListener {
            parentFragmentManager.commit {
                this@FavouriteFragment.dismiss()
                replace(R.id.mainContainer, SettingsFragment.newInstance())
                addToBackStack(SettingsFragment.TAG)
            }
        }
    }
}