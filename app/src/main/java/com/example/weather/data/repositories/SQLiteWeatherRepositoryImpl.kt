package com.example.weather.data.repositories

import com.example.weather.data.db.AppDatabase
import com.example.weather.data.db.entities.WeatherEntity
import com.example.weather.data.mappers.IEntityMapper
import com.example.weather.domain.entities.Weather
import com.example.weather.domain.repositories.ILocalWeatherRepository
import javax.inject.Inject

class LocalWeatherRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val weatherEntityToWeatherMapper: IEntityMapper<WeatherEntity, Weather>,
    private val weatherToWeatherEntityMapper: IEntityMapper<Weather, WeatherEntity>,
) : ILocalWeatherRepository {

    override suspend fun getWeatherList(locationId: Int): List<Weather> {
        return database.weatherDao().getWeatherListByLocationId(locationId).map {
            weatherEntityToWeatherMapper.mapEntity(it)
        }
    }

    override suspend fun insertWeatherList(list: List<Weather>) {
        database.weatherDao().insertWeatherList(
            list.map { weatherToWeatherEntityMapper.mapEntity(it) }
        )
    }

    override suspend fun updateWeather(weather: Weather) {
        database.weatherDao().updateWeather(weatherToWeatherEntityMapper.mapEntity(weather))
    }

    override suspend fun getWeather(locationId: Int, day: Int): Weather {
        return weatherEntityToWeatherMapper.mapEntity(
            database.weatherDao().getWeather("${locationId}_${day}")
        )
    }
}