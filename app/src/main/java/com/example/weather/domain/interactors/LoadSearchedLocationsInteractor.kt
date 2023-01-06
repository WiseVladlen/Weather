package com.example.weather.domain.interactors

import com.example.weather.domain.entities.SearchedLocation
import com.example.weather.domain.repositories.ILocalLocationRepository
import com.example.weather.domain.common.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val EXCEPTION_INSUFFICIENT_LENGTH = "Insufficient length"
private const val EXCEPTION_EMPTY_REQUEST = "Empty request"

private const val MIN_REQUEST_LENGTH = 2

class LoadSearchedLocationsInteractor @Inject constructor(private val repository: ILocalLocationRepository) {

    fun load(query: String?): Result<Flow<List<SearchedLocation>>> {
        return when {
            query.isNullOrBlank() -> {
                Result.Error(Exception(EXCEPTION_EMPTY_REQUEST))
            }
            query.length >= MIN_REQUEST_LENGTH -> {
                Result.Success(repository.getLocationsByQueryFlow(query))
            }
            else -> {
                Result.Error(Exception(EXCEPTION_INSUFFICIENT_LENGTH))
            }
        }
    }
}