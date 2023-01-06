package com.example.weather.data.api

import com.example.weather.data.api.entities.WeatherForecastResponse
import com.example.weather.utils.APP_ID
import retrofit2.Response
import retrofit2.http.Query
import retrofit2.http.GET

private const val UNITS = "metric"

interface WeatherApi {

    @GET("data/2.5/onecall?exclude=current,minutely,hourly,alerts")
    suspend fun getWeatherListByLatAndLon(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("units") units: String = UNITS,
        @Query("appid") appId: String = APP_ID,
    ): Response<WeatherForecastResponse>
}