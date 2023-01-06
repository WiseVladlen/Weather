package com.example.weather.presentation.di.modules

import com.example.weather.data.api.GeocodingApi
import com.example.weather.data.api.RetrofitService
import com.example.weather.data.api.WeatherApi
import com.example.weather.utils.BASE_URL
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun getWeatherApi(): WeatherApi {
        return RetrofitService().getRetrofit(BASE_URL).create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun getGeocodingApi(): GeocodingApi {
        return RetrofitService().getRetrofit(BASE_URL).create(GeocodingApi::class.java)
    }
}