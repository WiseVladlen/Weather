package com.example.weather.domain.interactors

import com.example.weather.R
import com.example.weather.domain.repositories.ILocalWeatherRepository
import com.example.weather.presentation.WeatherApplication
import com.example.weather.utils.Formatter.getFormattedDate
import com.example.weather.utils.Formatter.getFormattedNumber
import javax.inject.Inject
import kotlin.math.roundToInt

class LoadWeatherForecastInteractor @Inject constructor(
    private val localWeatherRepository: ILocalWeatherRepository,
) {

    suspend fun load(locationId: Int, day: Int): String {
        val weather = localWeatherRepository.getWeather(locationId, day)

        val date = getFormattedDate(weather.date, weather.timezone)

        val dayTemp = getFormattedNumber(weather.temp.day)
        val minTemp = getFormattedNumber(weather.temp.min)
        val maxTemp = getFormattedNumber(weather.temp.max)

        val humidity = weather.humidity.roundToInt()

        return WeatherApplication.INSTANCE.getString(
            R.string.message_weather_forecast,
            date,
            dayTemp,
            minTemp,
            maxTemp,
            humidity,
        )
    }
}