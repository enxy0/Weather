package com.enxy.weather.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.DayForecast
import com.enxy.weather.utils.extension.withSign
import kotlinx.android.synthetic.main.item_day.view.*

class DayAdapter : RecyclerView.Adapter<DayAdapter.DayHolder>() {
    private val dayDataModelArrayList = ArrayList<DayForecast>()

    fun updateData(dayForecastList: ArrayList<DayForecast>) {
        this.dayDataModelArrayList.clear()
        this.dayDataModelArrayList.addAll(dayForecastList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayHolder(view)
    }

    override fun getItemCount() = dayDataModelArrayList.size

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.bind(dayDataModelArrayList[position])
    }

    inner class DayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dayForecast: DayForecast) = with(itemView) {
            temperature.text = context.getString(
                R.string.day_forecast_temperature,
                dayForecast.highestTemp.withSign(),
                dayForecast.lowestTemp.withSign()
            )
            date.text = context.getString(R.string.day_forecast_date, dayForecast.date)
            day.text = dayForecast.day
            weatherIcon.setImageResource(dayForecast.imageId)
        }
    }
}