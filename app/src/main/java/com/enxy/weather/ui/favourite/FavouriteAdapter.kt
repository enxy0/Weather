package com.enxy.weather.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.Location
import com.enxy.weather.ui.favourite.FavouriteAdapter.FavouriteHolder
import kotlinx.android.synthetic.main.item_location.view.*
import java.util.*
import kotlin.collections.ArrayList

class FavouriteAdapter(private val locationListener: FavouriteLocationListener) :
    RecyclerView.Adapter<FavouriteHolder>() {
    private val favouriteLocationsList = ArrayList<Location>()

    fun updateData(favouriteLocationsList: ArrayList<Location>) {
        this.favouriteLocationsList.clear()
        this.favouriteLocationsList.addAll(favouriteLocationsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return FavouriteHolder(view)
    }

    override fun getItemCount(): Int = favouriteLocationsList.size

    override fun onBindViewHolder(holder: FavouriteHolder, position: Int) {
        holder.bind(favouriteLocationsList[position])
    }

    inner class FavouriteHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(location: Location) {
            itemView.locationName.text = location.locationName
            itemView.locationName.setOnClickListener {
                locationListener.onLocationClick(location)
            }
        }
    }

    interface FavouriteLocationListener : EventListener {
        fun onLocationClick(location: Location)
    }
}