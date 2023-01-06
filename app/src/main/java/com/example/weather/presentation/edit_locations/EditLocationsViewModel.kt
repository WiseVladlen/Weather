package com.example.weather.presentation.edit_locations

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.entities.Location
import com.example.weather.domain.interactors.*
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Provider

private val TAG = EditLocationsViewModel::class.java.simpleName

class EditLocationsViewModel(
    private val updateLocationInteractor: UpdateLocationInteractor,
    private val loadEditableFavouriteLocationsInteractor: LoadEditableFavouriteLocationsInteractor,
    loadCurrentLocationInfoInteractor: LoadCurrentLocationInfoInteractor,
    private val updateCurrentLocationStateInteractor: UpdateCurrentLocationStateInteractor,
    loadInfoAboutDatabaseStatusInteractor: LoadInfoAboutDatabaseStatusInteractor,
) : ViewModel() {

    private val jobLoad: CompletableJob = SupervisorJob()
    private val jobUpdate: CompletableJob = SupervisorJob()

    private val _locationList = MutableLiveData<List<Location>>()
    val locationList: LiveData<List<Location>> = _locationList

    val initialCurrentLocationState = loadCurrentLocationInfoInteractor.load().asLiveData()

    val databaseStatus = loadInfoAboutDatabaseStatusInteractor.getDatabaseStatus().asLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO + jobLoad) {
            try {
                loadEditableFavouriteLocationsInteractor.load().collect { list ->
                    _locationList.postValue(list)
                }
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO + jobUpdate) {
            try {
                updateLocationInteractor.delete(location.id)
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    fun setCurrentLocationState(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO + jobUpdate) {
            try {
                updateCurrentLocationStateInteractor.update(state)
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

    class EditLocationsViewModelFactory @Inject constructor(
        private val updateLocationInteractor: Provider<UpdateLocationInteractor>,
        private val loadEditableFavouriteLocationsInteractor: Provider<LoadEditableFavouriteLocationsInteractor>,
        private val loadCurrentLocationInfoInteractor: Provider<LoadCurrentLocationInfoInteractor>,
        private val updateCurrentLocationStateInteractor: Provider<UpdateCurrentLocationStateInteractor>,
        private val loadInfoAboutDatabaseStatusInteractor: Provider<LoadInfoAboutDatabaseStatusInteractor>,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditLocationsViewModel(
                updateLocationInteractor.get(),
                loadEditableFavouriteLocationsInteractor.get(),
                loadCurrentLocationInfoInteractor.get(),
                updateCurrentLocationStateInteractor.get(),
                loadInfoAboutDatabaseStatusInteractor.get(),
            ) as T
        }
    }
}