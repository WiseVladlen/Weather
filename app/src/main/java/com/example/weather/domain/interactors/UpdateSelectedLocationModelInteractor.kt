package com.example.weather.domain.interactors

import android.content.Context
import com.example.weather.R
import com.example.weather.domain.common.SelectedLocationType
import com.example.weather.domain.entities.Location
import com.example.weather.domain.models.SelectedLocationModel
import com.example.weather.domain.repositories.ILocalLocationRepository
import com.example.weather.utils.CURRENT_LOCATION_ID
import com.example.weather.utils.UNINITIALIZED
import com.example.weather.utils.location.LocationManagerHelper.isLocationPermissionsGranted
import javax.inject.Inject

class UpdateSelectedLocationModelInteractor @Inject constructor(
    private val context: Context,
    private val localLocationRepository: ILocalLocationRepository,
    private val selectedLocationModel: SelectedLocationModel,
) {

    private val selectedLocation = selectedLocationModel.location.value

    suspend fun update(id: Int) = update(localLocationRepository.getLocation(id))

    suspend fun selectCurrentLocation() {
        val location = localLocationRepository.getLocation(CURRENT_LOCATION_ID)

        if (location.name == UNINITIALIZED || location.country == UNINITIALIZED) {
            update(null)
        } else {
            update(location)
        }
    }

    fun update(location: Location?) {
        when {
            location != null && location.id == CURRENT_LOCATION_ID && !context.isLocationPermissionsGranted() -> {
                selectedLocationModel.updateFields(
                    title = context.getString(R.string.title_current_location),
                    location = null,
                    type = SelectedLocationType.WITHOUT_LOCATION_PERMISSIONS,
                )
            }
            location != null -> {
                selectedLocationModel.updateFields(
                    title = location.name,
                    location = location,
                    type = SelectedLocationType.NOT_NULL,
                )
            }
            selectedLocation != null -> {
                selectedLocationModel.updateFields(
                    title = selectedLocation.name,
                    location = selectedLocation,
                    type = SelectedLocationType.NOT_NULL,
                )
            }
            else -> {
                selectedLocationModel.updateFields(
                    title = String(),
                    location = null,
                    type = SelectedLocationType.NULL,
                )
            }
        }
    }

    fun chooseSelectedLocation(list: List<Location>) {
        val location = list.find { it.id == selectedLocation?.id }

        if (list.isNotEmpty() && !list.contains(location)) {
            update(list.first())
        } else if ((list.isNotEmpty() && list.contains(location)) || list.isEmpty()) {
            update(null)
        }
    }
}