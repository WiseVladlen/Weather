package com.example.weather.presentation.search_locations

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.weather.domain.common.Result
import com.example.weather.domain.entities.SearchedLocation
import com.example.weather.domain.interactors.LoadSearchedLocationsInteractor
import com.example.weather.domain.interactors.UpdateSelectedLocationModelInteractor
import com.example.weather.domain.interactors.UpdateLocationInteractor
import com.example.weather.utils.MESSAGE_LOCATIONS_NOT_FOUND
import com.example.weather.utils.MESSAGE_LOCATIONS_SEARCH
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Provider

private val TAG = SearchLocationsViewModel::class.java.simpleName

data class SearchResultMessageModel(
    val title: String? = null,
    val visibility: Int,
)

class SearchLocationsViewModel(
    private val loadSearchedLocationsInteractor: LoadSearchedLocationsInteractor,
    private val updateLocationInteractor: UpdateLocationInteractor,
    private val updateSelectedLocationModelInteractor: UpdateSelectedLocationModelInteractor,
) : ViewModel() {

    private val jobLoad: CompletableJob = SupervisorJob()
    private val jobUpdate: CompletableJob = SupervisorJob()

    private val _searchResultMessage = MutableLiveData<SearchResultMessageModel>()
    val searchResultMessage: LiveData<SearchResultMessageModel> = _searchResultMessage

    private val _recyclerViewVisibility = MutableLiveData<Int>()
    val recyclerViewVisibility: LiveData<Int> = _recyclerViewVisibility

    private val _filteredLocationList = MutableLiveData<List<SearchedLocation>>()
    val filteredLocationList: LiveData<List<SearchedLocation>> = _filteredLocationList

    val searchQuery = MutableStateFlow<String?>(null)

    init {
        viewModelScope.launch(Dispatchers.IO + jobLoad) {
            try {
                collectLocationsByQuery()
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    private suspend fun collectLocationsByQuery() {
        searchQuery.collectLatest { query ->
            when (val result = loadSearchedLocationsInteractor.load(query)) {
                is Result.Success -> {
                    result.data.onStart {
                            _recyclerViewVisibility.postValue(View.GONE)
                            postMessage(MESSAGE_LOCATIONS_SEARCH, View.VISIBLE)
                        }
                        .collect { list ->
                            showSearchResult(list)
                            if (list.isEmpty()) {
                                postMessage(MESSAGE_LOCATIONS_NOT_FOUND, View.VISIBLE)
                            }
                        }
                }
                is Result.Error -> {
                    showSearchResult(emptyList())
                    result.exception.message?.let { Log.e(TAG, it) }
                }
            }
        }
    }

    private fun showSearchResult(list: List<SearchedLocation>) {
        postMessage(visibility = View.GONE)
        _filteredLocationList.postValue(list)
        _recyclerViewVisibility.postValue(View.VISIBLE)
    }

    private fun postMessage(title: String? = null, visibility: Int) {
        _searchResultMessage.postValue(SearchResultMessageModel(title, visibility))
    }

    fun addLocation(location: SearchedLocation, navigationCallback: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO + jobUpdate) {
            try {
                updateSelectedLocationModelInteractor.update(location.id)
                updateLocationInteractor.add(location.id)
                withContext(Dispatchers.Main) { navigationCallback() }
            } catch (exception: Exception) {
                exception.message?.let { Log.e(TAG, it) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobLoad.cancel()
        jobUpdate.cancel()
    }

    class SearchLocationsFragmentFactory @Inject constructor(
        private val loadSearchedLocationsInteractor: Provider<LoadSearchedLocationsInteractor>,
        private val updateLocationInteractor: Provider<UpdateLocationInteractor>,
        private val updateSelectedLocationModelInteractor: Provider<UpdateSelectedLocationModelInteractor>,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchLocationsViewModel(
                loadSearchedLocationsInteractor.get(),
                updateLocationInteractor.get(),
                updateSelectedLocationModelInteractor.get(),
            ) as T
        }
    }
}