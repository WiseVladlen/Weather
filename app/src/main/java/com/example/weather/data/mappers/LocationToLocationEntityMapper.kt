package com.example.weather.data.mappers

import com.example.weather.data.db.entities.LocationEntity
import com.example.weather.domain.entities.Location
import javax.inject.Inject

class LocationToLocationEntityMapper @Inject constructor() :
    IEntityMapper<Location, LocationEntity> {
    override fun mapEntity(entity: Location): LocationEntity {
        return LocationEntity(
            id = entity.id,
            lon = entity.coord.lon,
            lat = entity.coord.lat,
            name = entity.name,
            country = entity.country,
            viewType = entity.viewType,
        )
    }
}