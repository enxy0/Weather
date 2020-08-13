package com.enxy.weather.ui.weather

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enxy.weather.R
import com.enxy.weather.data.entity.DayForecast
import com.enxy.weather.utils.extension.dp
import com.enxy.weather.utils.extension.withSign
import kotlinx.android.synthetic.main.item_day.view.*

class DayAdapter : ListAdapter<DayForecast, DayAdapter.DayHolder>(DayForecastDiffCallback()) {
    companion object {
        private val EXPANDED_HEIGHT = 90.dp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_day, parent, false)
        return DayHolder(view)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Creates view expand/collapse animation
     */
    private fun animateState(view: View) {
        val expanded = view.height > 0 && view.width > 0

        val currentHeight: Int
        val newHeight: Int

        val currentAlpha: Float
        val newAlpha: Float

        when (expanded) {
            true -> {
                currentHeight = EXPANDED_HEIGHT
                newHeight = 0

                currentAlpha = 1f
                newAlpha = 0f
            }
            false -> {
                currentHeight = 0
                newHeight = EXPANDED_HEIGHT

                currentAlpha = 0f
                newAlpha = 1f
            }
        }
        val heightAnim = ValueAnimator.ofInt(currentHeight, newHeight).apply {
            duration = 200L
            addUpdateListener {
                view.layoutParams.height = it.animatedValue as Int
                view.requestLayout()
            }
        }
        val alphaAnim = ObjectAnimator.ofFloat(view, View.ALPHA, currentAlpha, newAlpha).apply {
            duration = 200L
        }
        AnimatorSet().apply {
            interpolator = AccelerateDecelerateInterpolator()
            playTogether(alphaAnim, heightAnim)
            start()
        }
    }

    inner class DayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dayForecast: DayForecast) = with(itemView) {
            temperature.text = context.getString(
                R.string.day_forecast_temperature,
                dayForecast.highestTemp.value.withSign(),
                dayForecast.lowestTemp.value.withSign()
            )
            date.text = context.getString(R.string.day_forecast_date, dayForecast.date)
            day.text = dayForecast.day
            weatherIcon.setImageResource(dayForecast.imageId)
            previewLayout.setOnClickListener {
                animateState(detailsLayout)
            }
        }
    }
}

class DayForecastDiffCallback : DiffUtil.ItemCallback<DayForecast>() {
    override fun areItemsTheSame(oldItem: DayForecast, newItem: DayForecast): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: DayForecast, newItem: DayForecast): Boolean {
        return oldItem == newItem
    }
}