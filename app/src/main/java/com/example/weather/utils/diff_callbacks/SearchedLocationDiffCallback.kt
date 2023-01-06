package com.example.weather.utils.diff_callbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.weather.domain.entities.SearchedLocation

object SearchedLocationDiffCallback : DiffUtil.ItemCallback<SearchedLocation>() {

    override fun areItemsTheSame(oldItem: SearchedLocation, newItem: SearchedLocation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchedLocation, newItem: SearchedLocation): Boolean {
        return oldItem == newItem
    }
}