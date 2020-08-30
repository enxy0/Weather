package com.enxy.weather.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.HourForecast
import kotlinx.android.synthetic.main.item_hour.view.*

class HourlyForecastAdapter :
    ListAdapter<HourForecast, HourlyForecastAdapter.HourHolder>(HourForecastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder {
        return HourHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_hour, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HourHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HourHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(hourForecastList: HourForecast) {
            with(itemView) {
                temperature.text = hourForecastList.temperature.toString()
                time.text = hourForecastList.time
                description.setImageResource(hourForecastList.imageId)
            }
        }
    }
}

class HourForecastDiffCallback : DiffUtil.ItemCallback<HourForecast>() {
    override fun areItemsTheSame(oldItem: HourForecast, newItem: HourForecast): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HourForecast, newItem: HourForecast): Boolean {
        return oldItem == newItem
    }
}