package com.example.weather.presentation.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.FragmentDetailedWeatherBinding
import com.example.weather.domain.entities.Weather
import com.example.weather.presentation.WeatherApplication
import com.example.weather.utils.Formatter.getFormattedDate
import com.example.weather.utils.Formatter.getFormattedNumber
import kotlin.math.roundToInt

class DetailedWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding by viewBinding<FragmentDetailedWeatherBinding>()

    fun bind(weather: Weather) {
        val date = getFormattedDate(weather.date, weather.timezone)

        val dayTemp = getFormattedNumber(weather.temp.day)
        val minTemp = getFormattedNumber(weather.temp.min)
        val maxTemp = getFormattedNumber(weather.temp.max)

        val humidity = weather.humidity.roundToInt()

        with(binding) {
            WeatherApplication.INSTANCE.let { context ->
                textViewForecastDate.text = context.getString(R.string.forecast_date_field, date)
                textViewTemp.text = context.getString(R.string.temp_field, dayTemp)
                textViewMinTemp.text = context.getString(R.string.temp_field, minTemp)
                textViewMaxTemp.text = context.getString(R.string.temp_field, maxTemp)
                textViewHumidityValue.text = context.getString(R.string.humidity_field, humidity)

                humidityPieChartView.apply {
                    setAngles(weather.humidity.times(360f / 100f), 0f)
                    animateView()
                }
                return@let
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): DetailedWeatherViewHolder {
            return DetailedWeatherViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.fragment_detailed_weather, parent, false)
            )
        }
    }
}