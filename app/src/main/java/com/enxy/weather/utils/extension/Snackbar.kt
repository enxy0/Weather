package com.enxy.weather.utils.extension

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun snackbarShort(rootView: View, anchorView: View = rootView, @StringRes description: Int) {
    Snackbar.make(rootView, description, Snackbar.LENGTH_SHORT).apply {
        setAnchorView(anchorView)
        show()
    }
}

fun snackbarLong(rootView: View, anchorView: View = rootView, @StringRes description: Int) {
    Snackbar.make(rootView, description, Snackbar.LENGTH_LONG).apply {
        setAnchorView(anchorView)
        show()
    }
}

fun snackbarAction(
    rootView: View,
    anchorView: View,
    @StringRes description: Int,
    @StringRes actionText: Int,
    action: () -> Unit
) {
    Snackbar.make(rootView, description, Snackbar.LENGTH_INDEFINITE).apply {
        setAction(actionText) {
            action()
            dismiss()
        }
        setAnchorView(anchorView)
        show()
    }
}

fun snackbarShort(rootView: View, @StringRes description: Int) {
    Snackbar.make(rootView, description, Snackbar.LENGTH_SHORT).show()
}

fun snackbarLong(rootView: View,  @StringRes description: Int) {
    Snackbar.make(rootView, description, Snackbar.LENGTH_LONG).show()
}

fun snackbarAction(
    rootView: View,
    @StringRes description: Int,
    @StringRes actionText: Int,
    action: () -> Unit
) {
    Snackbar.make(rootView, description, Snackbar.LENGTH_INDEFINITE).apply {
        setAction(actionText) {
            action()
            dismiss()
        }
        show()
    }
}