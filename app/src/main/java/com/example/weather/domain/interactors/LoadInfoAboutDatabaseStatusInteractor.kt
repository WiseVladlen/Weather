package com.example.weather.domain.interactors

import com.example.weather.domain.common.DatabaseStatus
import com.example.weather.domain.repositories.ILocalLocationRepository
import com.example.weather.utils.isNotNull
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LoadInfoAboutDatabaseStatusInteractor @Inject constructor(private val repository: ILocalLocationRepository) {

    fun getDatabaseStatus(): Flow<DatabaseStatus> {
        val firstRandomLocationFlow = repository.getFirstRandomLocationFlow()
        val firstFavoriteLocationFlow = repository.getFirstFavoriteLocationFlow()

        return firstRandomLocationFlow.zip(firstFavoriteLocationFlow) { randomLocation, favoriteLocation ->
            when {
                favoriteLocation.isNotNull() && randomLocation.isNotNull() -> DatabaseStatus.SELECTED_LOCATIONS_LOADED
                !favoriteLocation.isNotNull() && randomLocation.isNotNull() -> DatabaseStatus.UNSELECTED_LOCATIONS_LOADED
                else -> DatabaseStatus.LOCATIONS_NOT_LOADED
            }
        }
    }
}