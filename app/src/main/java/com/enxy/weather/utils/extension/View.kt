package com.enxy.weather.utils.extension

import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.view.isVisible
import kotlin.math.hypot

fun View.startCircularRevealAnimation(
    listener: AnimatorListenerAdapter?,
    duration: Long = 400,
    delay: Long = 0
) {
    val x: Float = this.width / 2f
    val y: Float = this.height / 2f
    val radius: Float = hypot(x, y)

    val animator = ViewAnimationUtils.createCircularReveal(this, x.toInt(), y.toInt(), 0f, radius)
    listener?.let { animator.addListener(it) }
    animator.duration = duration
    /*
     * To provide smooth animations when you set startDelay - you must set view.visibility to true in
     * fun onAnimationStart() by overriding it.
     */
    if (delay == 0L) {
        this.isVisible = true
    } else
        animator.startDelay = delay
    animator.start()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}