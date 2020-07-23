package com.enxy.weather.utils.extension

import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Runs (invokes) function after the delay
 */
inline fun BottomSheetDialogFragment.dismiss(delay: Long, crossinline onDismiss: () -> Unit = {}) {
    lifecycleScope.launch {
        delay(delay)
        onDismiss()
        dismiss()
    }
}