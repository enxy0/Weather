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
import com.enxy.weather.utils.Pressure.HECTO_PASCALS
import com.enxy.weather.utils.Pressure.MILLIMETERS_OF_MERCURY
import com.enxy.weather.utils.Wind.KILOMETERS_PER_HOUR
import com.enxy.weather.utils.Wind.METERS_PER_SECOND
import com.enxy.weather.utils.extension.dp
import kotlinx.android.synthetic.main.detailed_temp_card_view.view.*
import kotlinx.android.synthetic.main.item_day.view.*

class DailyForecastAdapter :
    ListAdapter<DayForecast, DailyForecastAdapter.DayHolder>(DayForecastDiffCallback()) {
    companion object {
        private val EXPANDED_HEIGHT = 68.dp
        private const val DURATION = 200L
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
            duration = DURATION
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                view.layoutParams.height = it.animatedValue as Int
                view.requestLayout()
            }
        }

        val alphaAnim = ObjectAnimator.ofFloat(view, View.ALPHA, currentAlpha, newAlpha).apply {
            duration = DURATION
            interpolator = AccelerateDecelerateInterpolator()
        }

        val set = AnimatorSet()
        set.playTogether(alphaAnim, heightAnim)
        set.start()
    }

    private fun rotate(view: View) {
        val angle = if (view.rotation == 0f) 180f else 0f

        view.animate()
            .rotation(angle)
            .setDuration(DURATION)
            .start()
    }

    inner class DayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dayForecast: DayForecast) = with(itemView) {
            highestTemperature.text = context.getString(
                R.string.day_forecast_temperature,
                dayForecast.highestTemp.toString()
            )
            lowestTemperature.text = context.getString(
                R.string.day_forecast_temperature,
                dayForecast.lowestTemp.toString()
            )
            date.text = dayForecast.date
            day.text = dayForecast.day
            weatherIcon.setImageResource(dayForecast.imageId)
            expandButton.setOnClickListener {
                animateState(detailsLayout)
                rotate(expandButton)
            }
            previewLayout.setOnClickListener {
                animateState(detailsLayout)
                rotate(expandButton)
            }

            // Humidity
            humidityCard.value.text = dayForecast.humidity.toString()

            // Wind
            windCard.value.text = dayForecast.wind.toString()
            when(dayForecast.wind.unit) {
                METERS_PER_SECOND ->
                    windCard.unit.text = context.getString(R.string.wind_value_meters_per_second)
                KILOMETERS_PER_HOUR ->
                    windCard.unit.text = context.getString(R.string.wind_value_kilometers_per_hour)
            }

            // Pressure
            pressureCard.value.text = dayForecast.pressure.toString()
            when(dayForecast.pressure.unit) {
                MILLIMETERS_OF_MERCURY ->
                    pressureCard.unit.text = context.getString(R.string.pressure_value_millimeters)
                HECTO_PASCALS ->
                    pressureCard.unit.text = context.getString(R.string.pressure_value_pascals)
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