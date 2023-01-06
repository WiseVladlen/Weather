package com.example.weather.presentation

import android.app.Application
import com.example.weather.presentation.di.AppComponent
import com.example.weather.presentation.di.DaggerAppComponent

class WeatherApplication : Application() {

    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE : WeatherApplication
            private set
    }
}