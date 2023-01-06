package com.example.weather.presentation.main_activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.domain.common.DatabaseStatus
import com.example.weather.presentation.WeatherApplication
import com.example.weather.utils.views.hide
import com.example.weather.utils.views.show
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var mainActivityViewModelFactory: MainActivityViewModel.MainActivityViewModelFactory
    private val mainActivityViewModel by viewModels<MainActivityViewModel> {
        mainActivityViewModelFactory
    }

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    private val navGraph: NavGraph by lazy {
        navController.navInflater.inflate(R.navigation.navigation_graph)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WeatherApplication.INSTANCE.appComponent?.inject(this)

        observeDataChanges()
    }

    private fun observeDataChanges() {
        mainActivityViewModel.databaseStatus.distinctUntilChanged().observe(this) { value ->
            when (value) {
                DatabaseStatus.SELECTED_LOCATIONS_LOADED -> {
                    binding.loadingProgressIndicator.hide()
                    setNavigationGraph(R.id.mainFragment)
                }
                DatabaseStatus.UNSELECTED_LOCATIONS_LOADED -> {
                    binding.loadingProgressIndicator.hide()
                    setNavigationGraph(R.id.editLocationsFragment)
                }
                else -> binding.loadingProgressIndicator.show()
            }
        }
    }

    private fun setNavigationGraph(startDestId: Int) {
        if (navGraph.startDestinationId != startDestId) {
            navController.graph = navGraph.apply {
                setStartDestination(startDestId)
            }
        }
    }
}