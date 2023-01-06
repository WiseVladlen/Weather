package com.example.weather.data.repositories

import com.example.weather.data.api.GeocodingApi
import com.example.weather.data.mappers.LocationResponseToLocationMapper
import com.example.weather.domain.common.Result
import com.example.weather.domain.entities.Location
import com.example.weather.domain.repositories.IRemoteLocationRepository
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject

private const val EXCEPTION_EMPTY_LOCATION = "Empty location"
private const val EXCEPTION_TIMEOUT = "Connection time expired"

class RemoteLocationRepositoryImpl @Inject constructor(
    private val geocodingApi: GeocodingApi,
    private val locationResponseToLocationMapper: LocationResponseToLocationMapper,
) : IRemoteLocationRepository {

    override suspend fun getLocation(
        id: Int,
        latitude: Double,
        longitude: Double,
        viewType: Int,
    ): Result<Location> {
        return try {
            val response = geocodingApi.getLocationByLatAndLon(latitude, longitude)

            return if (response.isSuccessful) {
                val location = response.body()?.let { list ->
                    locationResponseToLocationMapper.mapEntity(
                        list.first(),
                        id,
                        latitude,
                        longitude,
                        viewType,
                    )
                }

                when (location) {
                    null -> Result.Error(Exception(EXCEPTION_EMPTY_LOCATION))
                    else -> Result.Success(location)
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