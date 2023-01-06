package com.example.weather.data.mappers

interface IEntityMapper<EntityFrom, EntityTo> {
    fun mapEntity(entity: EntityFrom): EntityTo
}