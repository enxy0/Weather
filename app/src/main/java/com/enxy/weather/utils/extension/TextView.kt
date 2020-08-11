package com.enxy.weather.utils.extension

import android.animation.ValueAnimator
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

/**
 * Increase/decrease animation of integer value.
 *
 * If [TextView.getText] is not a number, then start value is 0.
 *
 * @param newValue the value to animate
 * @param isSignShown plus (+) sign before value (+15)
 * @param duration length of animation. Default - 400L
 */
fun TextView.animateNumberChange(
    newValue: Int,
    isSignShown: Boolean = false,
    duration: Long = 400L
) {
    val oldValue: Int = text.toString().toIntOrNull() ?: 0
    val animator = ValueAnimator.ofInt(oldValue, newValue)
    animator.addUpdateListener {
        text = if (isSignShown) {
            (it.animatedValue as Int).withSign()
        } else {
            (it.animatedValue as Int).toString()
        }
    }
    animator.interpolator = FastOutSlowInInterpolator()
    animator.duration = duration
    animator.start()
}