package com.example.weather.data.api.entities

data class WeatherForecastResponse(
    val lat: Float,
    val lon: Float,
    val timezone: String,
    val daily: List<WeatherResponse>,
)

data class WeatherResponse(
    val dt: Long,
    val temp: Temperature,
    val humidity: Float,
)

data class Temperature(
    val day: Float,
    val min: Float,
    val max: Float,
)