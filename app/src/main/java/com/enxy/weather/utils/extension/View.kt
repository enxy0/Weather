package com.enxy.weather.utils.extension

import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes

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

fun View.addRipple(@AttrRes attr: Int = android.R.attr.selectableItemBackground) =
    TypedValue().run {
        context.theme.resolveAttribute(attr, this, true)
        setBackgroundResource(resourceId)
    }

fun View.addCircleRipple() = addRipple(android.R.attr.selectableItemBackgroundBorderless)
