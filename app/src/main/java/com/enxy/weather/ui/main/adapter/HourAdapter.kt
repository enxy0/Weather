package com.enxy.weather.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.extension.getDrawableByName
import com.enxy.weather.extension.px
import com.enxy.weather.model.HourDataModel
import kotlinx.android.synthetic.main.item_hour.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HourAdapter : RecyclerView.Adapter<HourAdapter.HourHolder>() {
    private val hourDataModelArrayList = ArrayList<HourDataModel>()

    fun updateData(hourDataModelArrayList: ArrayList<HourDataModel>) {
        this.hourDataModelArrayList.clear()
        this.hourDataModelArrayList.addAll(hourDataModelArrayList)
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
        fun bind(hourDataModel: HourDataModel) {
            with(itemView) {
                temperatureTextView.text = hourDataModel.temperature
                timeTextView.text = hourDataModel.time
                CoroutineScope(Dispatchers.IO).launch {
                    val drawable = context!!.getDrawableByName(hourDataModel.image)
                    withContext(Dispatchers.Main) {
                        descriptionImageView.setImageDrawable(drawable)
                    }
                }
            }
        }
    }
}