package com.example.weather.presentation.search_locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.SearchedLocationLayoutBinding
import com.example.weather.domain.entities.SearchedLocation

class SearchedLocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding<SearchedLocationLayoutBinding>()

    fun bind(location: SearchedLocation, listener: SearchedLocationListener) {
        with(binding.root) {
            text = location.title

            setOnClickListener { listener.onClick(location) }
        }
    }

    companion object {
        fun create(parent: ViewGroup): SearchedLocationViewHolder {
            return SearchedLocationViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.searched_location_layout, parent, false)
            )
        }
    }

    fun interface SearchedLocationListener {
        fun onClick(location: SearchedLocation)
    }
}