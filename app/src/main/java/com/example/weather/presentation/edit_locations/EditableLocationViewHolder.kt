package com.example.weather.presentation.edit_locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.LocationLayoutBinding
import com.example.weather.domain.entities.Location

class EditableLocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding<LocationLayoutBinding>()

    fun bind(location: Location, listener: EditableLocationListener) {
        with(binding.root) {
            text = location.name

            setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_delete, 0, 0, 0)
            setOnClickListener { listener.onClick(location) }
        }
    }

    companion object {
        fun create(parent: ViewGroup): EditableLocationViewHolder {
            return EditableLocationViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.location_layout, parent, false)
            )
        }
    }

    fun interface EditableLocationListener {
        fun onClick(editableLocation: Location)
    }
}