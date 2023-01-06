package com.example.weather.data.db.dao

import androidx.room.*
import com.example.weather.data.db.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(list: List<LocationEntity>)

    @Query("SELECT * FROM location LIMIT 1")
    fun getFirstRandomLocationFlow(): Flow<LocationEntity>

    @Query("SELECT * FROM location WHERE id = 1")
    fun getCurrentLocationFlow(): Flow<LocationEntity>

    @Query("SELECT * FROM location WHERE viewType = 3 LIMIT 1")
    fun getFavouriteCurrentLocationFlow(): Flow<LocationEntity>

    @Query("SELECT * FROM location WHERE id = :id")
    fun getLocationFlow(id: Int): Flow<LocationEntity>

    @Query("SELECT * FROM location WHERE viewType IN (1, 3) LIMIT 1")
    fun getFirstFavoriteLocationFlow(): Flow<LocationEntity>

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun getLocation(id: Int): LocationEntity

    @Query("SELECT * FROM location WHERE name LIKE '%' || :query || '%' AND viewType = 0 ORDER BY LENGTH(name) ASC, name ASC LIMIT 100")
    fun getLocationsByQueryFlow(query: String): Flow<List<LocationEntity>>

    @Query("SELECT * FROM location WHERE viewType IN (:viewTypes) ORDER BY timestamp, viewType DESC")
    fun getFilteredLocationsByTypeFlow(vararg viewTypes: Int): Flow<List<LocationEntity>>

    @Query("UPDATE location SET viewType = :viewType WHERE id = 1")
    suspend fun updateCurrentLocationType(viewType: Int)

    @Query("UPDATE location SET viewType = :viewType, timestamp = :timestamp WHERE id = :id")
    suspend fun updateLocation(id: Int, viewType: Int, timestamp: Long)
}