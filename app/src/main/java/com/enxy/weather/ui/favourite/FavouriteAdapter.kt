package com.enxy.weather.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.MiniForecast
import com.enxy.weather.ui.favourite.FavouriteAdapter.MiniForecastHolder
import com.enxy.weather.utils.extension.withSign
import kotlinx.android.synthetic.main.item_favourite.view.*

class FavouriteAdapter(private val onLocationChange: (MiniForecast) -> Unit) :
    RecyclerView.Adapter<MiniForecastHolder>() {
    private val data = ArrayList<MiniForecast>()

    fun updateData(data: List<MiniForecast>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class MiniForecastHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(miniForecast: MiniForecast) = with(itemView) {
            temperature.text = miniForecast.temperature.value.withSign()
            description.text = miniForecast.description
            locationName.text = miniForecast.locationName
            weatherIcon.setImageResource(miniForecast.imageId)
            card.setOnClickListener {
                onLocationChange(miniForecast)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiniForecastHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        return MiniForecastHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MiniForecastHolder, position: Int) {
        holder.bind(data[position])
    }
}