package com.example.weather.domain.repositories

import com.example.weather.domain.entities.Weather

interface ILocalWeatherRepository {

    suspend fun getWeatherList(locationId: Int): List<Weather>

    suspend fun insertWeatherList(list: List<Weather>)

    suspend fun updateWeather(weather: Weather)

    suspend fun getWeather(locationId: Int, day: Int): Weather
}