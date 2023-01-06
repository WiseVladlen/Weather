package com.example.weather.presentation.weather

import android.util.Log
import androidx.lifecycle.*
import com.example.weather.domain.common.Result
import com.example.weather.domain.models.SelectedLocationModel
import com.example.weather.domain.entities.Weather
import com.example.weather.domain.interactors.*
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Provider

private val TAG = WeatherViewModel::class.java.simpleName

class WeatherViewModel(
    private val selectedLocationModel: SelectedLocationModel,
    private val loadWeatherInteractor: LoadWeatherInteractor,
    private val loadWeatherModelInteractor: LoadWeatherModelInteractor,
    private val updateWeatherModelInteractor: UpdateWeatherModelInteractor,
) : ViewModel() {

    private val jobLoad: CompletableJob = SupervisorJob()

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _weatherList = MutableLiveData<List<Weather>>()
    val weatherList: LiveData<List<Weather>> = _weatherList

    val weatherLoadingResultMessage = loadWeatherModelInteractor.loadResultMessage()

    val lastSelectedDay: Int get() = loadWeatherModelInteractor.loadLastSelectedDay()

    fun setLastSelectedDay(item: Int) = updateWeatherModelInteractor.updateLastSelectedDay(item)

    init {
        viewModelScope.launch(Dispatchers.IO + jobLoad) {
            try {
                loadWeather()
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    fun getUpToDateWeatherForecast() {
        viewModelScope.launch(Dispatchers.IO + jobLoad) {
            try {
                loadWeather()
                _isRefreshing.postValue(false)
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    private suspend fun loadWeather() {
        when (val result = loadWeatherInteractor.load(selectedLocationModel.location.value)) {
            is Result.Success -> result.data.let { _weatherList.postValue(it) }
            is Result.Error -> {
                _weatherList.postValue(emptyList())
                result.exception.message?.let { Log.d(TAG, it) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobLoad.cancel()
    }

    class WeatherViewModelFactory @Inject constructor(
        private val selectedLocationModel: Provider<SelectedLocationModel>,
        private val loadWeatherInteractor: Provider<LoadWeatherInteractor>,
        private val loadWeatherModelInteractor: Provider<LoadWeatherModelInteractor>,
        private val updateWeatherModelInteractor: Provider<UpdateWeatherModelInteractor>,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WeatherViewModel(
                selectedLocationModel.get(),
                loadWeatherInteractor.get(),
                loadWeatherModelInteractor.get(),
                updateWeatherModelInteractor.get(),
            ) as T
        }
    }
}