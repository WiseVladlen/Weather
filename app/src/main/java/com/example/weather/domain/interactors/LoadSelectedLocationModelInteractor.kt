package com.example.weather.domain.interactors

import com.example.weather.domain.models.SelectedLocationModel
import javax.inject.Inject

class LoadSelectedLocationModelInteractor @Inject constructor(
    private val selectedLocationModel: SelectedLocationModel,
) {

    fun loadLocation() = selectedLocationModel.location

    fun loadLocationTitle() = selectedLocationModel.title

    fun loadLocationType() = selectedLocationModel.type
}