package com.example.weather.presentation.main_fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weather.domain.common.LocationViewType
import com.example.weather.domain.entities.Location
import com.example.weather.utils.diff_callbacks.LocationDiffCallback

class FavouriteLocationsAdapter(
    private val listener: LocationListener,
) : ListAdapter<Location, ViewHolder>(LocationDiffCallback) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            LocationViewType.CURRENT_SELECTED.value -> CurrentLocationViewHolder.create(viewGroup)
            else -> SimpleLocationViewHolder.create(viewGroup)
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getItem(position).let { item ->
            when (item.viewType) {
                LocationViewType.CURRENT_SELECTED.value -> {
                    (viewHolder as CurrentLocationViewHolder).bind(item, listener)
                }
                else -> {
                    (viewHolder as SimpleLocationViewHolder).bind(item, listener)
                }
            }
        }
    }

    override fun getItemViewType(position: Int) = currentList[position].viewType

    fun interface LocationListener {
        fun onClick(location: Location)
    }
}