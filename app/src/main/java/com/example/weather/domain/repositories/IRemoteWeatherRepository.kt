package com.example.weather.domain.repositories

import com.example.weather.domain.common.Result
import com.example.weather.domain.entities.Location
import com.example.weather.domain.entities.Weather

interface IRemoteWeatherRepository {

    suspend fun getWeatherList(location: Location): Result<List<Weather>>
}