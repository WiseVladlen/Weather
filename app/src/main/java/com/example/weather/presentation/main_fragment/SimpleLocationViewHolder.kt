package com.example.weather.presentation.main_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.LocationLayoutBinding
import com.example.weather.domain.entities.Location

class SimpleLocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding<LocationLayoutBinding>()

    fun bind(location: Location, listener: FavouriteLocationsAdapter.LocationListener) {
        with(binding.root) {
            text = location.name

            setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_location, 0, 0, 0)
            setOnClickListener {
                listener.onClick(location)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): SimpleLocationViewHolder {
            return SimpleLocationViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.location_layout, parent, false)
            )
        }
    }
}