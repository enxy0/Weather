package com.enxy.weather.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.MiniForecast
import com.enxy.weather.ui.favourite.FavouriteAdapter.MiniForecastHolder
import kotlinx.android.synthetic.main.item_location.view.*

class FavouriteAdapter(private val onLocationChange: (MiniForecast) -> Unit) :
    RecyclerView.Adapter<MiniForecastHolder>() {
    private val data = ArrayList<MiniForecast>()

    fun updateData(data: List<MiniForecast>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class MiniForecastHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(miniForecast: MiniForecast) {
            itemView.locationName.text = miniForecast.locationName
            itemView.locationName.setOnClickListener {
                onLocationChange(miniForecast)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniForecastHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return MiniForecastHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MiniForecastHolder, position: Int) {
        holder.bind(data[position])
    }
}