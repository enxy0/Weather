package com.enxy.weather.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.model.LocationInfo
import com.enxy.weather.ui.favourite.FavouriteAdpater.FavouriteHolder
import kotlinx.android.synthetic.main.item_favourite.view.*

class FavouriteAdpater : RecyclerView.Adapter<FavouriteHolder>() {
    private val locationInfoArrayList = ArrayList<LocationInfo>()

    fun updateData(locationInfoArrayList: ArrayList<LocationInfo>) {
        this.locationInfoArrayList.clear()
        this.locationInfoArrayList.addAll(locationInfoArrayList)
        notifyDataSetChanged()
    }

    inner class FavouriteHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.locationName.setOnClickListener {
                TODO("Implement opening forecast for current location")
            }
        }

        fun bind(locationInfo: LocationInfo) {
            itemView.locationName.text = locationInfo.locationName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        return FavouriteHolder(view)
    }

    override fun getItemCount(): Int = locationInfoArrayList.size

    override fun onBindViewHolder(holder: FavouriteHolder, position: Int) {
        holder.bind(locationInfoArrayList[position])
    }
}