package com.enxy.weather.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.DayWeather
import kotlinx.android.synthetic.main.item_day.view.*
import javax.inject.Inject

class DayAdapter @Inject constructor() : RecyclerView.Adapter<DayAdapter.DayHolder>() {
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
                dayNameTextView.text = dayWeather.dayName
                dateTextView.text = dayWeather.date
                highTemperatureTextView.text = dayWeather.temperatureHigh
                lowTemperatureTextView.text = dayWeather.temperatureLow
                descriptionImageView.setImageResource(dayWeather.imageId)
            }
        }
    }
}