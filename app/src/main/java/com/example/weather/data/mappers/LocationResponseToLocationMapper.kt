package com.example.weather.data.mappers

import com.example.weather.data.api.entities.LocationResponse
import com.example.weather.domain.entities.Coordinates
import com.example.weather.domain.entities.Location
import javax.inject.Inject

class LocationResponseToLocationMapper @Inject constructor() {
    fun mapEntity(
        entity: LocationResponse,
        id: Int,
        latitude: Double,
        longitude: Double,
        viewType: Int,
    ): Location {
        return Location(
            id = id,
            name = entity.name,
            country = entity.country,
            coord = Coordinates(
                lat = latitude.toFloat(),
                lon = longitude.toFloat(),
            ),
            viewType = viewType,
        )
    }
}