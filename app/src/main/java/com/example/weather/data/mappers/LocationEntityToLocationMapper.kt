package com.example.weather.data.mappers

import com.example.weather.data.db.entities.LocationEntity
import com.example.weather.domain.entities.Coordinates
import com.example.weather.domain.entities.Location
import javax.inject.Inject

class LocationEntityToLocationMapper @Inject constructor() :
    IEntityMapper<LocationEntity, Location> {
    override fun mapEntity(entity: LocationEntity): Location {
        return Location(
            id = entity.id,
            coord = Coordinates(
                lon = entity.lon,
                lat = entity.lat,
            ),
            country = entity.country,
            name = entity.name,
            viewType = entity.viewType,
        )
    }
}