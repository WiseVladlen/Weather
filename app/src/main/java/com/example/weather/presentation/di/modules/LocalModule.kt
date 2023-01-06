package com.example.weather.presentation.di.modules

import android.content.Context
import com.example.weather.domain.models.SelectedLocationModel
import com.example.weather.data.db.AppDatabase
import com.example.weather.domain.models.WeatherModel
import com.example.weather.presentation.WeatherApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Provides
    @Singleton
    fun getAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun getContext(): Context {
        return WeatherApplication.INSTANCE
    }

    @Provides
    @Singleton
    fun getSelectedLocationModel(): SelectedLocationModel {
        return SelectedLocationModel()
    }

    @Provides
    @Singleton
    fun getWeatherModel(): WeatherModel {
        return WeatherModel()
    }
}