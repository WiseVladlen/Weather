package com.example.weather.data.repositories

import com.example.weather.data.api.WeatherApi
import com.example.weather.data.api.entities.WeatherResponse
import com.example.weather.data.mappers.IEntityMapper
import com.example.weather.domain.common.Result
import com.example.weather.domain.entities.Location
import com.example.weather.domain.entities.Weather
import com.example.weather.domain.repositories.IRemoteWeatherRepository
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject

private const val EXCEPTION_EMPTY_REMOTE_LIST = "Empty remote list"
private const val EXCEPTION_TIMEOUT = "Connection time expired"

class RemoteWeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherForWeekResponseToWeatherMapper: IEntityMapper<WeatherResponse, Weather>,
) : IRemoteWeatherRepository {

    override suspend fun getWeatherList(location: Location): Result<List<Weather>> {
        return try {
            val response = weatherApi.getWeatherListByLatAndLon(location.coord.lat, location.coord.lon)

            return if (response.isSuccessful) {
                val weatherList = response.body()?.let { body ->
                    body.daily.mapIndexed { index, weather ->
                        weatherForWeekResponseToWeatherMapper.mapEntity(weather).apply {
                            this.id = "${location.id}_${index}"
                            this.locationId = location.id
                            this.timezone = body.timezone
                        }
                    }
                }

                when (weatherList) {
                    null -> Result.Error(Exception(EXCEPTION_EMPTY_REMOTE_LIST))
                    else -> Result.Success(weatherList)
                }
            } else {
                Result.Error(Exception(response.errorBody().toString()))
            }
        } catch (exception: SocketTimeoutException) {
            Result.Error(Exception(EXCEPTION_TIMEOUT))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}