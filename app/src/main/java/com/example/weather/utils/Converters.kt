package com.example.weather.utils

import androidx.room.TypeConverter
import com.example.weather.domain.common.LocationViewType

@TypeConverter
fun Boolean.toCurrentLocationViewType(): Int {
    return when (this) {
        true -> LocationViewType.CURRENT_SELECTED.value
        false -> LocationViewType.CURRENT_NOT_SELECTED.value
    }
}

@TypeConverter
fun Int.toCurrentLocationState(): Boolean {
    return when (this) {
        LocationViewType.CURRENT_SELECTED.value -> true
        else -> false
    }
}