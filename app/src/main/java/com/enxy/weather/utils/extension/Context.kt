package com.enxy.weather.utils.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat

internal fun Context.getDrawableByName(name: String): Drawable? {
    val resourceId = resources.getIdentifier(name, "drawable", packageName)
    Log.d("Context", "getDrawableByName: name=$name")
    return ContextCompat.getDrawable(this, resourceId)
}