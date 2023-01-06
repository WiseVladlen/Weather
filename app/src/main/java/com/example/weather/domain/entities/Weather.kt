package com.example.weather.domain.entities

import com.example.weather.data.api.entities.Temperature

data class Weather(
    var id: String? = null,
    val date: Long,
    val temp: Temperature,
    val humidity: Float,
    var timezone: String? = null,
    var locationId: Int? = null,
)