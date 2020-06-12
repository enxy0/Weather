package com.enxy.weather.utils.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.enxy.weather.utils.exception.Failure
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(this, Observer(body))

/**
 * Runs (invokes) function after the delay
 */
inline fun LifecycleOwner.runDelayed(delayTime: Long, crossinline fn: () -> Unit) {
    lifecycleScope.launch {
        delay(delayTime)
        fn()
    }
}