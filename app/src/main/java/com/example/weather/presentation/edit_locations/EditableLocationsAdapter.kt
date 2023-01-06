package com.example.weather.presentation.edit_locations

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.weather.domain.entities.Location
import com.example.weather.utils.diff_callbacks.LocationDiffCallback

class EditableLocationsAdapter(
    private val listener: EditableLocationViewHolder.EditableLocationListener,
) : ListAdapter<Location, EditableLocationViewHolder>(LocationDiffCallback) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EditableLocationViewHolder {
        return EditableLocationViewHolder.create(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: EditableLocationViewHolder, position: Int) {
        viewHolder.bind(getItem(position), listener)
    }
}