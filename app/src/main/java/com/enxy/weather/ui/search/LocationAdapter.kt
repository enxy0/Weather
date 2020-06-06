package com.enxy.weather.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.LocationInfo
import com.enxy.weather.ui.search.LocationAdapter.LocationHolder
import kotlinx.android.synthetic.main.item_location.view.*

class LocationAdapter(private val locationListener: LocationListener) :
    RecyclerView.Adapter<LocationHolder>() {
    private val data = ArrayList<LocationInfo>()

    fun updateData(data: ArrayList<LocationInfo>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class LocationHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(locationInfo: LocationInfo) {
            itemView.locationName.text = locationInfo.locationName
            itemView.locationName.setOnClickListener {
                locationListener.onLocationChange(locationInfo)
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

    interface LocationListener {
        fun onLocationChange(locationInfo: LocationInfo)
    }
}