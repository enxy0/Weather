package com.enxy.weather.utils.extension

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.animation.doOnEnd

/**
 * Changes image with scale and alpha animation
 *
 * @param resId Id of drawable resource
 */
fun ImageView.animateScaleFadeChange(@DrawableRes resId: Int) {
    // Start set begins the animation by scaling down and changing alpha to 0% (invisible)
    // Also performs image change on the end of animation
    val start = AnimatorSet()
    start.playTogether(scaleAnimator(0.8f), alphaAnimator(1f, 0f))
    start.doOnEnd {
        setImageResource(resId)
    }

    // End set continuous the animation by bringing view to the default state: scaling up and
    // changing alpha back to 100%
    val end = AnimatorSet()
    end.playTogether(scaleAnimator(1f), alphaAnimator(0f, 1f))

    // Sequence set plays animations (start and end) sequentially
    val sequence = AnimatorSet()
    sequence.duration = 300L
    sequence.playSequentially(start, end)
    sequence.start()
}

private fun ImageView.scaleAnimator(value: Float): ObjectAnimator {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, value)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, value)
    return ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
}

private fun ImageView.alphaAnimator(start: Float, end: Float): ObjectAnimator =
    ObjectAnimator.ofFloat(this, View.ALPHA, start, end)