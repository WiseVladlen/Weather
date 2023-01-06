package com.example.weather.presentation.di.modules

import com.example.weather.data.api.entities.WeatherResponse
import com.example.weather.data.db.entities.LocationEntity
import com.example.weather.data.db.entities.WeatherEntity
import com.example.weather.data.mappers.IEntityMapper
import com.example.weather.data.mappers.*
import com.example.weather.data.repositories.*
import com.example.weather.domain.entities.Location
import com.example.weather.domain.entities.SearchedLocation
import com.example.weather.domain.entities.Weather
import com.example.weather.domain.repositories.*
import dagger.Binds
import dagger.Module

@Module
abstract class BindingModule {

    @Binds
    abstract fun bindLocalLocationRepository(repository: SQLiteLocationRepositoryImpl): ILocalLocationRepository

    @Binds
    abstract fun bindLocalWeatherRepository(repository: LocalWeatherRepositoryImpl): ILocalWeatherRepository

    @Binds
    abstract fun bindRemoteWeatherRepository(repository: RemoteWeatherRepositoryImpl): IRemoteWeatherRepository

    @Binds
    abstract fun bindRemoteLocationRepository(repository: RemoteLocationRepositoryImpl): IRemoteLocationRepository

    @Binds
    abstract fun bindLocationEntityMapper(mapper: LocationEntityToLocationMapper): IEntityMapper<LocationEntity, Location>

    @Binds
    abstract fun bindLocationMapper(mapper: LocationToLocationEntityMapper): IEntityMapper<Location, LocationEntity>

    @Binds
    abstract fun bindWeatherEntityMapper(mapper: WeatherEntityToWeatherMapper): IEntityMapper<WeatherEntity, Weather>

    @Binds
    abstract fun bindWeatherMapper(mapper: WeatherToWeatherEntityMapper): IEntityMapper<Weather, WeatherEntity>

    @Binds
    abstract fun bindWeatherSubClassResponseToWeatherMapper(mapper: WeatherForecastResponseToWeatherMapper): IEntityMapper<WeatherResponse, Weather>

    @Binds
    abstract fun bindLocationEntityToSearchedLocationMapper(mapper: LocationEntityToSearchedLocationMapper): IEntityMapper<LocationEntity, SearchedLocation>
}