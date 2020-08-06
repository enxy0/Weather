package com.enxy.weather.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.HourForecast
import com.enxy.weather.utils.extension.withSign
import kotlinx.android.synthetic.main.item_hour.view.*

class HourAdapter : RecyclerView.Adapter<HourAdapter.HourHolder>() {
    private val hourDataModelArrayList = ArrayList<HourForecast>()

    fun updateData(hourForecastList: ArrayList<HourForecast>) {
        this.hourDataModelArrayList.clear()
        this.hourDataModelArrayList.addAll(hourForecastList)
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
        fun bind(hourForecastList: HourForecast) {
            with(itemView) {
                temperature.text = hourForecastList.temperature.value.withSign()
                time.text = hourForecastList.time
                description.setImageResource(hourForecastList.imageId)
            }
        }
    }
}