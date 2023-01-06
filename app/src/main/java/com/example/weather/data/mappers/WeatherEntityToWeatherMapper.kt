package com.example.weather.data.mappers

import com.example.weather.data.api.entities.Temperature
import com.example.weather.data.db.entities.WeatherEntity
import com.example.weather.domain.entities.Weather
import javax.inject.Inject

class WeatherEntityToWeatherMapper @Inject constructor() : IEntityMapper<WeatherEntity, Weather> {
    override fun mapEntity(entity: WeatherEntity): Weather {
        return Weather(
            id = entity.id,
            date = entity.date,
            temp = Temperature(
                day = entity.temp,
                min = entity.minTemp,
                max = entity.maxTemp,
            ),
            humidity = entity.humidity,
            timezone = entity.timezone,
            locationId = entity.locationId,
        )
    }
}