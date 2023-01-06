package com.example.weather.domain.repositories

import com.example.weather.domain.common.Result
import com.example.weather.domain.entities.Location

interface IRemoteLocationRepository {

    suspend fun getLocation(
        id: Int,
        latitude: Double,
        longitude: Double,
        viewType: Int,
    ): Result<Location>
}