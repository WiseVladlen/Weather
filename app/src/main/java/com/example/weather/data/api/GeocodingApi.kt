package com.example.weather.data.api

import com.example.weather.data.api.entities.LocationResponse
import com.example.weather.utils.APP_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {

    @GET("geo/1.0/reverse")
    suspend fun getLocationByLatAndLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = APP_ID,
    ): Response<List<LocationResponse>>
}