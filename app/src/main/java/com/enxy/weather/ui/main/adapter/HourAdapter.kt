package com.enxy.weather.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.ui.main.model.HourWeatherModel
import kotlinx.android.synthetic.main.item_hour.view.*
import javax.inject.Inject

class HourAdapter @Inject constructor() : RecyclerView.Adapter<HourAdapter.HourHolder>() {
    private val hourDataModelArrayList = ArrayList<HourWeatherModel>()

    fun updateData(hourWeatherModelArrayList: ArrayList<HourWeatherModel>) {
        this.hourDataModelArrayList.clear()
        this.hourDataModelArrayList.addAll(hourWeatherModelArrayList)
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
        fun bind(hourWeatherModel: HourWeatherModel) {
            with(itemView) {
                temperatureTextView.text = hourWeatherModel.temperature
                timeTextView.text = hourWeatherModel.time
                descriptionImageView.setImageResource(hourWeatherModel.imageId)
            }
        }
    }
}