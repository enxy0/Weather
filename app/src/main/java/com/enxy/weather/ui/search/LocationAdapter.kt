package com.enxy.weather.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.Location
import com.enxy.weather.ui.search.LocationAdapter.LocationHolder
import kotlinx.android.synthetic.main.item_location.view.*

class LocationAdapter(private val onLocationChange: (Location) -> Unit) :
    RecyclerView.Adapter<LocationHolder>() {
    private val data = ArrayList<Location>()

    fun updateData(data: ArrayList<Location>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

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

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.bind(data[position])
    }
}