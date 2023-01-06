package com.example.weather.domain.interactors

import com.example.weather.domain.repositories.ILocalLocationRepository
import javax.inject.Inject

class UpdateCurrentLocationStateInteractor @Inject constructor(
    private val localLocationRepository: ILocalLocationRepository,
) {

    suspend fun update(state: Boolean) = localLocationRepository.updateCurrentLocationType(state)
}