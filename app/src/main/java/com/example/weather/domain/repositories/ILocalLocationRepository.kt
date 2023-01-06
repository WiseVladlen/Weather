package com.example.weather.domain.repositories

import com.example.weather.domain.entities.Location
import com.example.weather.domain.entities.SearchedLocation
import kotlinx.coroutines.flow.Flow

interface ILocalLocationRepository {

    suspend fun upsert(location: Location)

    fun getCurrentLocationStateFlow(): Flow<Boolean>

    fun getFirstRandomLocationFlow(): Flow<Location?>

    fun getCurrentLocationFlow(): Flow<Location?>

    fun getFavouriteCurrentLocationFlow(): Flow<Location?>

    fun getLocationFlow(id: Int): Flow<Location?>

    fun getFirstFavoriteLocationFlow(): Flow<Location?>

    suspend fun getLocation(id: Int): Location

    fun getFavoriteLocationsFlow(): Flow<List<Location>>

    fun getEditableFavoriteLocationsFlow(): Flow<List<Location>>

    fun getLocationsByQueryFlow(query: String): Flow<List<SearchedLocation>>

    suspend fun updateCurrentLocationType(state: Boolean)

    suspend fun updateLocation(id: Int, viewType: Int, timestamp: Long)
}