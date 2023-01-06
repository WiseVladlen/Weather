package com.example.weather.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weather",
    foreignKeys = [ForeignKey(
        entity = LocationEntity::class,
        parentColumns = ["id"],
        childColumns = ["locationId"]
    )],
    indices = [
        Index(value = ["locationId"])
    ]
)
data class WeatherEntity(
    @PrimaryKey val id: String,
    val date: Long,
    val temp: Float,
    val minTemp: Float,
    val maxTemp: Float,
    val humidity: Float,
    val timezone: String,
    val locationId: Int,
)