package com.example.weather.domain.interactors

import com.example.weather.domain.repositories.ILocalLocationRepository
import javax.inject.Inject

class LoadCurrentLocationInfoInteractor @Inject constructor(
    private val localLocationRepository: ILocalLocationRepository,
) {

    fun load() = localLocationRepository.getCurrentLocationStateFlow()
}