package com.example.weather.domain.interactors

import android.util.Log
import com.example.weather.domain.common.Result
import com.example.weather.domain.entities.Location
import com.example.weather.domain.entities.Weather
import com.example.weather.domain.repositories.ILocalWeatherRepository
import com.example.weather.domain.repositories.IRemoteWeatherRepository
import com.example.weather.domain.models.WeatherModel
import com.example.weather.presentation.WeatherApplication
import com.example.weather.utils.*
import java.lang.Exception
import javax.inject.Inject

private val TAG = LoadWeatherInteractor::class.java.simpleName

private const val LOG_MESSAGE_EMPTY_LOCAL_LIST = "Empty local list"
private const val LOG_MESSAGE_NULL_LOCATION = "Null location"

class LoadWeatherInteractor @Inject constructor(
    private val weatherModel: WeatherModel,
    private val localWeatherRepository: ILocalWeatherRepository,
    private val remoteWeatherRepository: IRemoteWeatherRepository,
) {

    suspend fun load(location: Location?): Result<List<Weather>> {
        if (location == null) {
            return Result.Error(Exception(LOG_MESSAGE_NULL_LOCATION))
        }

        val localResponse = localWeatherRepository.getWeatherList(location.id)

        if (!WeatherApplication.INSTANCE.isNetworkAvailable()) {
            weatherModel.setLoadResultMessage(MESSAGE_NO_CONNECTION)
        } else {
            when (val remoteResponse = remoteWeatherRepository.getWeatherList(location)) {
                is Result.Success -> {
                    upsertWeatherList(
                        localList = localResponse,
                        remoteList = remoteResponse.data,
                    )
                    return remoteResponse
                }
                is Result.Error -> {
                    weatherModel.setLoadResultMessage(MESSAGE_ERROR_HAS_OCCURRED)
                    remoteResponse.exception.message?.let { Log.d(TAG, it) }
                }
            }
        }

        return when (localResponse.isEmpty()) {
            true -> Result.Error(Exception(LOG_MESSAGE_EMPTY_LOCAL_LIST))
            false -> Result.Success(localResponse)
        }
    }

    private suspend fun upsertWeatherList(localList: List<Weather>, remoteList: List<Weather>) {
        if (localList.isEmpty()) {
            localWeatherRepository.insertWeatherList(remoteList)
        } else {
            localList.zip(remoteList).forEach {
                if (it.first != it.second) {
                    localWeatherRepository.updateWeather(it.second)
                }
            }
        }

        when (localList.containsAll(remoteList)) {
            true -> weatherModel.setLoadResultMessage(MESSAGE_DATA_IS_UP_TO_DATA)
            false -> weatherModel.setLoadResultMessage(MESSAGE_FORECAST_UPDATED)
        }
    }
}