package com.enxy.weather.utils.extension

import android.view.View

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

/**
 * Creates fade in animation of the hidden view
 *
 * @param duration
 */
fun View.fadeIn(duration: Long = 500L) {
    alpha = 0f
    visibility = View.VISIBLE

    animate()
        .setDuration(duration)
        .alpha(1f)
        .start()
}