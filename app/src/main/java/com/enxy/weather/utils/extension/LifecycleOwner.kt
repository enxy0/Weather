package com.enxy.weather.utils.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Runs (invokes) function after the delay
 */
inline fun LifecycleOwner.runDelayed(delayTime: Long, crossinline fn: () -> Unit) {
    lifecycleScope.launch {
        delay(delayTime)
        fn()
    }
}