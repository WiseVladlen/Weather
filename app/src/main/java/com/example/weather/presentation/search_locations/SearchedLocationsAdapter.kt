package com.example.weather.presentation.search_locations

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.weather.domain.entities.SearchedLocation
import com.example.weather.utils.diff_callbacks.SearchedLocationDiffCallback

class SearchedLocationsAdapter(
    private val listener: SearchedLocationViewHolder.SearchedLocationListener,
) : ListAdapter<SearchedLocation, SearchedLocationViewHolder>(SearchedLocationDiffCallback) {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchedLocationViewHolder {
        return SearchedLocationViewHolder.create(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: SearchedLocationViewHolder, position: Int) {
        viewHolder.bind(getItem(position), listener)
    }
}