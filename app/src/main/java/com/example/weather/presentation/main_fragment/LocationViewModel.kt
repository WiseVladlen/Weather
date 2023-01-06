package com.example.weather.presentation.main_fragment

import android.util.Log
import androidx.lifecycle.*
import com.example.weather.domain.entities.Location
import com.example.weather.domain.interactors.*
import com.example.weather.utils.SingleEvent
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Provider

private val TAG = LocationViewModel::class.java.simpleName

class LocationViewModel(
    private val loadWeatherModelInteractor: LoadWeatherModelInteractor,
    private val loadWeatherForecastInteractor: LoadWeatherForecastInteractor,
    private val loadSelectedLocationModelInteractor: LoadSelectedLocationModelInteractor,
    private val loadFavoriteLocationsInteractor: LoadFavoriteLocationsInteractor,
    private val upsertCurrentLocationInteractor: UpsertCurrentLocationInteractor,
    private val updateSelectedLocationModelInteractor: UpdateSelectedLocationModelInteractor,
) : ViewModel() {

    private val jobLoad: CompletableJob = SupervisorJob()
    private val jobUpdate: CompletableJob = SupervisorJob()

    private val _locationList = MutableLiveData<List<Location>>()
    val locationList: LiveData<List<Location>> = _locationList

    private val _weatherForecast = MutableLiveData<SingleEvent<String>>()
    val weatherForecastMessage: LiveData<SingleEvent<String>> = _weatherForecast

    val selectedLocationType = loadSelectedLocationModelInteractor.loadLocationType()
    val selectedLocationTitle = loadSelectedLocationModelInteractor.loadLocationTitle()

    init {
        viewModelScope.launch(Dispatchers.IO + jobLoad) {
            try {
                loadFavoriteLocationsInteractor.load().collect { list ->
                    _locationList.postValue(list)
                    updateSelectedLocationModelInteractor.chooseSelectedLocation(list)
                }
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    fun setSelectedLocation(location: Location?) {
        viewModelScope.launch(Dispatchers.IO + jobUpdate) {
            try {
                updateSelectedLocationModelInteractor.update(location)
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    fun selectCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO + jobUpdate) {
            try {
                updateSelectedLocationModelInteractor.selectCurrentLocation()
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    fun setCurrentLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO + jobUpdate) {
            try {
                upsertCurrentLocationInteractor.upsert(latitude, longitude)
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    fun shareWeatherForecast() {
        viewModelScope.launch(Dispatchers.IO + jobLoad) {
            try {
                loadSelectedLocationModelInteractor.loadLocation().value?.id?.let { locationId ->
                    val lastSelectedDay = loadWeatherModelInteractor.loadLastSelectedDay()

                    loadWeatherForecastInteractor.load(locationId, lastSelectedDay).let { message ->
                        _weatherForecast.postValue(SingleEvent(message))
                    }
                }
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobLoad.cancel()
        jobUpdate.cancel()
    }

    class LocationViewModelFactory @Inject constructor(
        private val loadWeatherModelInteractor: Provider<LoadWeatherModelInteractor>,
        private val loadWeatherForecastInteractor: Provider<LoadWeatherForecastInteractor>,
        private val loadSelectedLocationModelInteractor: Provider<LoadSelectedLocationModelInteractor>,
        private val loadFavoriteLocationsInteractor: Provider<LoadFavoriteLocationsInteractor>,
        private val upsertCurrentLocationInteractor: Provider<UpsertCurrentLocationInteractor>,
        private val updateSelectedLocationModelInteractor: Provider<UpdateSelectedLocationModelInteractor>,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LocationViewModel(
                loadWeatherModelInteractor.get(),
                loadWeatherForecastInteractor.get(),
                loadSelectedLocationModelInteractor.get(),
                loadFavoriteLocationsInteractor.get(),
                upsertCurrentLocationInteractor.get(),
                updateSelectedLocationModelInteractor.get(),
            ) as T
        }
    }
}