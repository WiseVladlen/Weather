package com.example.weather.domain.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.domain.common.SelectedLocationType
import com.example.weather.domain.entities.Location
import com.example.weather.utils.SingleEvent

class SelectedLocationModel {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _location = MutableLiveData<Location?>()
    val location: LiveData<Location?> = _location

    private val _type = MutableLiveData<SingleEvent<SelectedLocationType>>()
    val type: LiveData<SingleEvent<SelectedLocationType>> = _type

    fun updateFields(title: String, location: Location?, type: SelectedLocationType) {
        _title.postValue(title)
        _location.postValue(location)
        _type.postValue(SingleEvent(type))
    }
}