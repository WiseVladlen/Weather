package com.example.weather.data.db.dao

import androidx.room.*
import com.example.weather.data.db.entities.WeatherEntity

@Dao
interface WeatherDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherList(list: List<WeatherEntity>)

    @Query("SELECT * FROM weather WHERE id = :id")
    suspend fun getWeather(id: String): WeatherEntity

    @Query("SELECT * FROM weather WHERE locationId = :locationId ORDER BY date ASC")
    suspend fun getWeatherListByLocationId(locationId: Int): List<WeatherEntity>

    @Update
    suspend fun updateWeather(weather: WeatherEntity)
}