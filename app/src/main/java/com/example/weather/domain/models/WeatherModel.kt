package com.example.weather.domain.models

import androidx.lifecycle.MutableLiveData
import com.example.weather.utils.SingleEvent

class WeatherModel {

    val loadResultMessage = MutableLiveData<SingleEvent<String>>()

    var lastSelectedDay: Int = 0

    fun setLoadResultMessage(message: String) {
        loadResultMessage.postValue(SingleEvent(message))
    }
}