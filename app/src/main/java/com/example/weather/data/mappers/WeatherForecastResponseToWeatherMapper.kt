package com.example.weather.data.mappers

import com.example.weather.data.api.entities.Temperature
import com.example.weather.data.api.entities.WeatherResponse
import com.example.weather.domain.entities.Weather
import javax.inject.Inject

class WeatherForecastResponseToWeatherMapper @Inject constructor() :
    IEntityMapper<WeatherResponse, Weather> {
    override fun mapEntity(entity: WeatherResponse): Weather {
        return Weather(
            date = entity.dt,
            temp = Temperature(
                day = entity.temp.day,
                min = entity.temp.min,
                max = entity.temp.max,
            ),
            humidity = entity.humidity,
        )
    }
}