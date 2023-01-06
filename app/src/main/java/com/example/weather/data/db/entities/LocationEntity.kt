package com.example.weather.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.domain.common.LocationViewType

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val lon: Float,
    val lat: Float,
    val country: String,
    val viewType: Int,
    val timestamp: Long = 0,
)