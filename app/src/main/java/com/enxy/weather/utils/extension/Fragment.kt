package com.enxy.weather.utils.extension

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.openLink(@StringRes resId: Int) {
    val link = Intent(Intent.ACTION_VIEW, Uri.parse(getString(resId)))
    startActivity(link)
}

fun Fragment.openLink(url: String) {
    val link = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(link)
}