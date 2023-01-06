package com.example.weather.domain.interactors

import android.content.Context
import android.util.Log
import com.example.weather.domain.common.Result
import com.example.weather.domain.repositories.ILocalLocationRepository
import com.example.weather.domain.repositories.IRemoteLocationRepository
import com.example.weather.utils.CURRENT_LOCATION_ID
import com.example.weather.utils.isNetworkAvailable
import javax.inject.Inject

private val TAG = UpsertCurrentLocationInteractor::class.java.simpleName

class UpsertCurrentLocationInteractor @Inject constructor(
    private val context: Context,
    private val localLocationRepository: ILocalLocationRepository,
    private val remoteLocationRepository: IRemoteLocationRepository,
) {

    suspend fun upsert(latitude: Double, longitude: Double) {
        val localResponse = localLocationRepository.getLocation(CURRENT_LOCATION_ID)

        if (context.isNetworkAvailable() && (localResponse.coord.lat != latitude.toFloat() || localResponse.coord.lon != longitude.toFloat())) {
            when (val remoteResponse = remoteLocationRepository.getLocation(
                id = localResponse.id,
                latitude = latitude,
                longitude = longitude,
                viewType = localResponse.viewType,
            )) {
                is Result.Success -> {
                    localLocationRepository.upsert(remoteResponse.data)
                }
                is Result.Error -> {
                    remoteResponse.exception.message?.let { Log.d(TAG, it) }
                }
            }
        }
    }
}