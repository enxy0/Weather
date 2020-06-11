package com.enxy.weather.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.DayWeather
import kotlinx.android.synthetic.main.item_day.view.*

class DayAdapter : RecyclerView.Adapter<DayAdapter.DayHolder>() {
    private val dayDataModelArrayList = ArrayList<DayWeather>()

    fun updateData(dayWeatherArrayList: ArrayList<DayWeather>) {
        this.dayDataModelArrayList.clear()
        this.dayDataModelArrayList.addAll(dayWeatherArrayList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        return DayHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_day, parent, false
            )
        )
    }

    override fun getItemCount() = dayDataModelArrayList.size

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.bind(dayDataModelArrayList[position])
    }

    inner class DayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dayWeather: DayWeather) {
            with(itemView) {
                Log.d("DayHolder", "bind: dayWeatherModel=$dayWeather")
                day.text = dayWeather.dayName
                date.text = dayWeather.date
                highestTemperature.text = dayWeather.temperatureHigh
                lowestTemperature.text = dayWeather.temperatureLow
                description.setImageResource(dayWeather.imageId)
            }
        }
    }
}