package com.example.weather.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.pow
import kotlin.math.round

object Formatter {

    private const val DATE_FORMAT = "d MMMM yyyy"

    private const val BASE = 10.0
    private const val DIGIT = 1.0

    fun getFormattedDate(date: Long, timeZone: String?): String {
        val simpleDateFormatTime = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).apply {
            this.timeZone = TimeZone.getTimeZone(timeZone)
        }

        return simpleDateFormatTime.format(Date(TimeUnit.SECONDS.toMillis(date)))
    }

    fun getFormattedNumber(value: Number, digit: Double = DIGIT): String {
        val tmp = BASE.pow(digit)

        return "${round(value.toDouble() * tmp) / tmp}"
    }
}