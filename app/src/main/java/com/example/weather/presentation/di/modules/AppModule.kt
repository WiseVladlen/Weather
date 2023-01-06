package com.example.weather.presentation.di.modules

import dagger.Module

@Module(includes = [BindingModule::class, LocalModule::class, RemoteModule::class])
class AppModule