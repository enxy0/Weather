package com.enxy.weather.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.extension.dp
import com.enxy.weather.extension.getDrawableByName
import com.enxy.weather.extension.px
import com.enxy.weather.model.DayDataModel
import kotlinx.android.synthetic.main.item_day.view.*
import kotlinx.android.synthetic.main.item_day.view.descriptionImageView
import kotlinx.android.synthetic.main.item_hour.view.*

class DayAdapter : RecyclerView.Adapter<DayAdapter.DayHolder>() {
    private val dayDataModelArrayList = ArrayList<DayDataModel>()

    fun updateData(dayDataModelArrayList: ArrayList<DayDataModel>) {
        this.dayDataModelArrayList.clear()
        this.dayDataModelArrayList.addAll(dayDataModelArrayList)
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
        fun bind(dayDataModel: DayDataModel) {
            with(itemView) {
                Log.d("DayHolder", "bind: dayDataModel=$dayDataModel")
                dayNameTextView.text = dayDataModel.day
                dateTextView.text = dayDataModel.date
                highTemperatureTextView.text = dayDataModel.temperatureHigh
                lowTemperatureTextView.text = dayDataModel.temperatureLow
                descriptionImageView.setImageDrawable(context.getDrawableByName(dayDataModel.image))
            }
        }
    }
}