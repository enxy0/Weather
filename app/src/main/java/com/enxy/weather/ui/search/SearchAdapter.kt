package com.enxy.weather.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.Location
import com.enxy.weather.ui.search.SearchAdapter.LocationHolder
import kotlinx.android.synthetic.main.item_location.view.*

class SearchAdapter(private val onLocationChange: (Location) -> Unit) :
    ListAdapter<Location, LocationHolder>(SearchDiffCallback()) {

    inner class LocationHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(location: Location) {
            itemView.locationName.text = location.locationName
            itemView.locationName.setOnClickListener {
                onLocationChange(location)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return LocationHolder(view)
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SearchDiffCallback : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.locationName == newItem.locationName
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }
}