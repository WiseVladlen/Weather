package com.example.weather.domain.interactors

import com.example.weather.domain.repositories.ILocalLocationRepository
import javax.inject.Inject

class LoadFavoriteLocationsInteractor @Inject constructor(private val repository: ILocalLocationRepository) {

    fun load() = repository.getFavoriteLocationsFlow()
}