package com.enxy.weather.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.ui.main.MainViewModel
import com.enxy.weather.ui.search.LocationAdapter.LocationHolder
import kotlinx.android.synthetic.main.item_location.view.*

class LocationAdapter(private val fragment: SearchFragment, private val viewModel: MainViewModel) :
    RecyclerView.Adapter<LocationHolder>() {
    private val data = ArrayList<LocationInfo>()

    fun updateData(data: ArrayList<LocationInfo>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class LocationHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(locationInfo: LocationInfo) {
            itemView.locationName.text = locationInfo.formattedLocationName
            itemView.parentLinearLayout.setOnClickListener {
                viewModel.updateWeatherLocation(locationInfo)
                fragment.hideKeyboard()
                fragment.parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return LocationHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.bind(data[position])
    }
}