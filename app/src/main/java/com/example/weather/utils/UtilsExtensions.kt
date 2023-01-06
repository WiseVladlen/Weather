package com.example.weather.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> T.isNotNull(): Boolean {
    return when (this) {
        null -> false
        else -> true
    }
}

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            observer.onChanged(value)
            removeObserver(this)
        }
    })
}

inline fun <T, R: Any> Flow<T>.mapSafely(crossinline transform: suspend (T) -> R): Flow<R?> {
    return this.map { value ->
        value?.let { transform(it) }
    }
}