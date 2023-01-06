package com.example.weather.utils.diff_callbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.weather.domain.entities.Weather

object WeatherDiffCallback : DiffUtil.ItemCallback<Weather>() {

    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem == newItem
    }
}