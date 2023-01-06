package com.example.weather.domain.interactors

import com.example.weather.domain.models.WeatherModel
import javax.inject.Inject

class LoadWeatherModelInteractor @Inject constructor(
    private val weatherModel: WeatherModel,
) {

    fun loadResultMessage() = weatherModel.loadResultMessage

    fun loadLastSelectedDay() = weatherModel.lastSelectedDay
}