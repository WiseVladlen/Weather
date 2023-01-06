package com.example.weather.presentation.di

import com.example.weather.presentation.di.modules.AppModule
import com.example.weather.presentation.edit_locations.EditLocationsFragment
import com.example.weather.presentation.main_activity.MainActivity
import com.example.weather.presentation.search_locations.SearchLocationsFragment
import com.example.weather.presentation.main_fragment.MainFragment
import com.example.weather.presentation.weather.WeatherContainerFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: MainFragment)

    fun inject(fragment: WeatherContainerFragment)

    fun inject(fragment: SearchLocationsFragment)

    fun inject(fragment: EditLocationsFragment)
}