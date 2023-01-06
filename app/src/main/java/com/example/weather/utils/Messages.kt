package com.example.weather.utils

import com.example.weather.R
import com.example.weather.presentation.WeatherApplication

private val context = WeatherApplication.INSTANCE

val MESSAGE_NO_CONNECTION = context.getString(R.string.message_no_connection)
val MESSAGE_LOCATIONS_SEARCH = context.getString(R.string.message_locations_search)
val MESSAGE_LOCATIONS_NOT_FOUND = context.getString(R.string.message_locations_not_found)
val MESSAGE_FORECAST_UPDATED = context.getString(R.string.message_forecast_updated)
val MESSAGE_DATA_IS_UP_TO_DATA = context.getString(R.string.message_data_is_up_to_date)
val MESSAGE_ERROR_HAS_OCCURRED = context.getString(R.string.message_error_has_occurred)