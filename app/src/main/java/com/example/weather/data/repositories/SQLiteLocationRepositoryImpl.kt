package com.example.weather.data.repositories

import com.example.weather.data.db.AppDatabase
import com.example.weather.data.db.entities.LocationEntity
import com.example.weather.data.mappers.IEntityMapper
import com.example.weather.domain.common.LocationViewType
import com.example.weather.domain.entities.Location
import com.example.weather.domain.entities.SearchedLocation
import com.example.weather.domain.repositories.ILocalLocationRepository
import com.example.weather.utils.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SQLiteLocationRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val locationEntityToLocationMapper: IEntityMapper<LocationEntity, Location>,
    private val locationToLocationEntityMapper: IEntityMapper<Location, LocationEntity>,
    private val locationEntityToSearchedLocationMapper: IEntityMapper<LocationEntity, SearchedLocation>,
) : ILocalLocationRepository {

    override suspend fun upsert(location: Location) {
        database.locationDao().insert(locationToLocationEntityMapper.mapEntity(location))
    }

    override fun getCurrentLocationStateFlow(): Flow<Boolean> {
        return database.locationDao().getCurrentLocationFlow().map { location ->
            location.viewType.toCurrentLocationState()
        }
    }

    override fun getFirstRandomLocationFlow(): Flow<Location?> {
        return database.locationDao().getFirstRandomLocationFlow().mapSafely { location ->
            locationEntityToLocationMapper.mapEntity(location)
        }
    }

    override fun getCurrentLocationFlow(): Flow<Location?> {
        return database.locationDao().getCurrentLocationFlow().mapSafely { location ->
            locationEntityToLocationMapper.mapEntity(location)
        }
    }

    override fun getFavouriteCurrentLocationFlow(): Flow<Location?> {
        return database.locationDao().getFavouriteCurrentLocationFlow().mapSafely { location ->
            locationEntityToLocationMapper.mapEntity(location)
        }
    }

    override fun getLocationFlow(id: Int): Flow<Location?> {
        return database.locationDao().getLocationFlow(id).mapSafely { location ->
            locationEntityToLocationMapper.mapEntity(location)
        }
    }

    override fun getFirstFavoriteLocationFlow(): Flow<Location?> {
        return database.locationDao().getFirstFavoriteLocationFlow().mapSafely { location ->
            locationEntityToLocationMapper.mapEntity(location)
        }
    }

    override suspend fun getLocation(id: Int): Location {
        return database.locationDao().getLocation(id).let { location ->
            locationEntityToLocationMapper.mapEntity(location)
        }
    }

    override fun getFavoriteLocationsFlow(): Flow<List<Location>> {
        return database.locationDao().getFilteredLocationsByTypeFlow(
            LocationViewType.SIMPLE_SELECTED.value,
            LocationViewType.CURRENT_SELECTED.value,
        ).map { list ->
            list.map { location ->
                locationEntityToLocationMapper.mapEntity(location)
            }
        }
    }

    override fun getEditableFavoriteLocationsFlow(): Flow<List<Location>> {
        return database.locationDao().getFilteredLocationsByTypeFlow(
            LocationViewType.SIMPLE_SELECTED.value,
        ).map { list ->
            list.map { location ->
                locationEntityToLocationMapper.mapEntity(location)
            }
        }
    }

    override fun getLocationsByQueryFlow(query: String): Flow<List<SearchedLocation>> {
        return database.locationDao().getLocationsByQueryFlow(query).map { list ->
            list.map { location ->
                locationEntityToSearchedLocationMapper.mapEntity(location)
            }
        }
    }

    override suspend fun updateCurrentLocationType(state: Boolean) {
        database.locationDao().updateCurrentLocationType(state.toCurrentLocationViewType())
    }

    override suspend fun updateLocation(id: Int, viewType: Int, timestamp: Long) {
        database.locationDao().updateLocation(id, viewType, timestamp)
    }
}