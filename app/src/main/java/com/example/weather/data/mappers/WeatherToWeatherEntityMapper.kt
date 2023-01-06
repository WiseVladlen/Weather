package com.example.weather.data.mappers

import com.example.weather.data.db.entities.WeatherEntity
import com.example.weather.domain.entities.Weather
import javax.inject.Inject

private const val EXCEPTION_ID_VALUE_WAS_NULL = "Id value was null"
private const val EXCEPTION_LOCATION_ID_VALUE_WAS_NULL = "LocationId value was null"
private const val EXCEPTION_TIMEZONE_VALUE_WAS_NULL = "Timezone value was null"

class WeatherToWeatherEntityMapper @Inject constructor() : IEntityMapper<Weather, WeatherEntity> {
    override fun mapEntity(entity: Weather): WeatherEntity {
        val id = checkNotNull(entity.id) {
            EXCEPTION_ID_VALUE_WAS_NULL
        }
        val locationId = checkNotNull(entity.locationId) {
            EXCEPTION_LOCATION_ID_VALUE_WAS_NULL
        }
        val timezone = checkNotNull(entity.timezone) {
            EXCEPTION_TIMEZONE_VALUE_WAS_NULL
        }
        return WeatherEntity(
            id = id,
            date = entity.date,
            temp = entity.temp.day,
            minTemp = entity.temp.min,
            maxTemp = entity.temp.max,
            humidity = entity.humidity,
            timezone = timezone,
            locationId = locationId,
        )
    }
}