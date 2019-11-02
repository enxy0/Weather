package com.enxy.weather.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.ui.main.model.DayWeatherModel
import kotlinx.android.synthetic.main.item_day.view.*
import javax.inject.Inject

class DayAdapter @Inject constructor() : RecyclerView.Adapter<DayAdapter.DayHolder>() {
    private val dayDataModelArrayList = ArrayList<DayWeatherModel>()

    fun updateData(dayWeatherModelArrayList: ArrayList<DayWeatherModel>) {
        this.dayDataModelArrayList.clear()
        this.dayDataModelArrayList.addAll(dayWeatherModelArrayList)
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
        fun bind(dayWeatherModel: DayWeatherModel) {
            with(itemView) {
                Log.d("DayHolder", "bind: dayWeatherModel=$dayWeatherModel")
                dayNameTextView.text = dayWeatherModel.dayName
                dateTextView.text = dayWeatherModel.date
                highTemperatureTextView.text = dayWeatherModel.temperatureHigh
                lowTemperatureTextView.text = dayWeatherModel.temperatureLow
                descriptionImageView.setImageResource(dayWeatherModel.imageId)
            }
        }
    }
}