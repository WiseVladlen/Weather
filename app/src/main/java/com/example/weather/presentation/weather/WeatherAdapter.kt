package com.example.weather.presentation.weather

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.weather.domain.entities.Weather
import com.example.weather.utils.diff_callbacks.WeatherDiffCallback

class WeatherAdapter : ListAdapter<Weather, DetailedWeatherViewHolder>(WeatherDiffCallback) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DetailedWeatherViewHolder {
        return DetailedWeatherViewHolder.create(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: DetailedWeatherViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }
}