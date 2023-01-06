package com.example.weather.domain.interactors

import com.example.weather.domain.common.LocationViewType
import com.example.weather.domain.repositories.ILocalLocationRepository
import javax.inject.Inject

class UpdateLocationInteractor @Inject constructor(
    private val localLocationRepository: ILocalLocationRepository,
) {

    suspend fun add(id: Int) {
        localLocationRepository.updateLocation(id, LocationViewType.SIMPLE_SELECTED.value, System.currentTimeMillis())
    }

    suspend fun delete(id: Int) {
        localLocationRepository.updateLocation(id, LocationViewType.SIMPLE_NOT_SELECTED.value, 0)
    }
}