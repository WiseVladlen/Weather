package com.example.weather.presentation.main_activity

import androidx.lifecycle.*
import com.example.weather.domain.interactors.LoadInfoAboutDatabaseStatusInteractor
import javax.inject.Inject
import javax.inject.Provider

class MainActivityViewModel(
    loadInfoAboutDatabaseStatusInteractor: LoadInfoAboutDatabaseStatusInteractor,
) : ViewModel() {

    val databaseStatus = loadInfoAboutDatabaseStatusInteractor.getDatabaseStatus().asLiveData()

    class MainActivityViewModelFactory @Inject constructor(
        private val loadInfoAboutDatabaseStatusInteractor: Provider<LoadInfoAboutDatabaseStatusInteractor>,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainActivityViewModel(loadInfoAboutDatabaseStatusInteractor.get()) as T
        }
    }
}