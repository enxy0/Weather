package com.enxy.weather.utils.extension

import android.content.res.Resources

val Int.pixelsToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.dpToPixels: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()