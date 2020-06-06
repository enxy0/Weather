package com.enxy.weather.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.Hour
import kotlinx.android.synthetic.main.item_hour.view.*
import javax.inject.Inject

class HourAdapter @Inject constructor() : RecyclerView.Adapter<HourAdapter.HourHolder>() {
    private val hourDataModelArrayList = ArrayList<Hour>()

    fun updateData(hourForecastArrayList: ArrayList<Hour>) {
        this.hourDataModelArrayList.clear()
        this.hourDataModelArrayList.addAll(hourForecastArrayList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder {
        return HourHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hour, parent, false
            )
        )
    }

    override fun getItemCount() = hourDataModelArrayList.size

    override fun onBindViewHolder(holder: HourHolder, position: Int) {
        holder.bind(hourDataModelArrayList[position])
    }

    inner class HourHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(hourForecast: Hour) {
            with(itemView) {
                temperatureTextView.text = hourForecast.temperature
                timeTextView.text = hourForecast.time
                descriptionImageView.setImageResource(hourForecast.imageId)
            }
        }
    }
}