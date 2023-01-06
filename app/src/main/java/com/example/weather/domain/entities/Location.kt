package com.example.weather.domain.entities

import com.example.weather.domain.common.LocationViewType

data class Location(
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coordinates,
    val viewType: Int = LocationViewType.SIMPLE_NOT_SELECTED.value,
)

data class Coordinates(
    val lon: Float,
    val lat: Float,
)