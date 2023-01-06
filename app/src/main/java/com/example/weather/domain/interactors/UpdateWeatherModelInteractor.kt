package com.example.weather.domain.interactors

import com.example.weather.domain.models.WeatherModel
import javax.inject.Inject

class UpdateWeatherModelInteractor @Inject constructor(
    private val weatherModel: WeatherModel,
) {

    fun updateLastSelectedDay(item: Int) {
        weatherModel.lastSelectedDay = item
    }
}