package com.example.weather.data.mappers

import com.example.weather.data.db.entities.LocationEntity
import com.example.weather.domain.entities.SearchedLocation
import javax.inject.Inject

class LocationEntityToSearchedLocationMapper @Inject constructor() :
    IEntityMapper<LocationEntity, SearchedLocation> {
    override fun mapEntity(entity: LocationEntity): SearchedLocation {
        return SearchedLocation(
            id = entity.id,
            title = "${entity.name}, ${entity.country}",
        )
    }
}