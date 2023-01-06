package com.example.weather.presentation.main_fragment

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.presentation.WeatherApplication
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.databinding.FragmentMainBinding
import com.example.weather.domain.common.SelectedLocationType
import com.example.weather.utils.*
import com.example.weather.utils.location.CustomLocationListener
import com.example.weather.utils.location.LocationManagerHelper.requestLocationUpdates
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var locationViewModelFactory: LocationViewModel.LocationViewModelFactory
    private val locationViewModel by viewModels<LocationViewModel> { locationViewModelFactory }

    private val binding by viewBinding(FragmentMainBinding::bind)

    private var onDrawerClosedListener: SingleEvent<OnDrawerClosedListener?> = SingleEvent(null)

    private val locationsAdapter = FavouriteLocationsAdapter { location ->
        onDrawerClosedListener = SingleEvent(OnDrawerClosedListener {
            locationViewModel.setSelectedLocation(location)
        })
        binding.drawerLayout.close()
    }

    private var locationManager: LocationManager? = null

    private val locationListener: LocationListener = object : CustomLocationListener {
        override fun onLocationChanged(location: Location) {
            locationViewModel.setCurrentLocation(location.latitude, location.longitude)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        childFragmentManager.setFragmentResultListener(LOCATION_ACCESS_REQUEST_KEY, this) { _, bundle ->
            if (bundle.getBoolean(LOCATION_ACCESS_BUNDLE_KEY)) {
                locationViewModel.selectCurrentLocation()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        observeDataChanges()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        WeatherApplication.INSTANCE.appComponent?.inject(this)
    }

    private fun initializeViews() {
        setupLocationsRecyclerView()
        setupDrawerLayout()
        setupActionBar()
        setupNavigationMenu()
    }

    private fun observeDataChanges() {
        locationViewModel.apply {
            locationList.observe(viewLifecycleOwner) { list ->
                locationsAdapter.submitList(list)
            }

            selectedLocationType.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let { locationType ->
                    when (locationType) {
                        SelectedLocationType.NULL -> clearFragmentContainer()
                        SelectedLocationType.NOT_NULL -> setupWeatherContainerFragment()
                        SelectedLocationType.WITHOUT_LOCATION_PERMISSIONS -> setupRequestLocationAccessFragment()
                    }
                }
            }

            selectedLocationTitle.observe(viewLifecycleOwner) { title ->
                binding.textViewTitle.text = title
            }

            weatherForecastMessage.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled().let { message ->
                    if (message != null) {
                        requireContext().shareMessage(message)
                    }
                }
            }
        }
    }

    private fun setupDrawerLayout() {
        binding.drawerLayout.addDrawerListener(object : CustomDrawerListener {
            override fun onDrawerClosed(drawerView: View) {
                onDrawerClosedListener.getContentIfNotHandled()?.onClose()
            }
        })
    }

    private fun setupLocationsRecyclerView() {
        binding.navMenu.locationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = locationsAdapter
        }
    }

    private fun setupActionBar() {
        with(binding) {
            mainToolbar.setNavigationOnClickListener {
                drawerLayout.open()
            }

            buttonAdd.setOnClickListener {
                requireNavController().navigateSafely(MainFragmentDirections.actionMainFragmentToSearchLocationsFragment())
            }
        }
    }

    private fun setupNavigationMenu() {
        with(binding) {
            navMenu.itemEditLocations.setOnClickListener {
                onDrawerClosedListener = SingleEvent(OnDrawerClosedListener {
                    requireNavController().navigateSafely(MainFragmentDirections.actionMainFragmentToEditLocationsFragment())
                })
                drawerLayout.close()
            }

            navMenu.itemShareWeatherForecast.setOnClickListener {
                locationViewModel.shareWeatherForecast()
                drawerLayout.close()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        locationManager?.requestLocationUpdates(locationListener)
    }

    override fun onPause() {
        super.onPause()
        locationManager?.removeUpdates(locationListener)
    }

    private interface CustomDrawerListener : DrawerLayout.DrawerListener {
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) { }
        override fun onDrawerOpened(drawerView: View) { }
        override fun onDrawerClosed(drawerView: View) { }
        override fun onDrawerStateChanged(newState: Int) { }
    }

    private fun interface OnDrawerClosedListener {
        fun onClose()
    }
}