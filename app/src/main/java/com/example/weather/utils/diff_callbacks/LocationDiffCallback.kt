package com.example.weather.utils.diff_callbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.weather.domain.entities.Location

object LocationDiffCallback : DiffUtil.ItemCallback<Location>() {

    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }
}