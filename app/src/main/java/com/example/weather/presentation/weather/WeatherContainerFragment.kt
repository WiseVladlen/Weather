package com.example.weather.presentation.weather

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.FragmentWeatherContainerBinding
import com.example.weather.presentation.WeatherApplication
import com.example.weather.utils.views.builders.SnackBarBuilder
import javax.inject.Inject

class WeatherContainerFragment : Fragment(R.layout.fragment_weather_container) {

    @Inject
    lateinit var weatherViewModelFactory: WeatherViewModel.WeatherViewModelFactory
    private val weatherViewModel by viewModels<WeatherViewModel> { weatherViewModelFactory }

    private val binding by viewBinding(FragmentWeatherContainerBinding::bind)

    private val weatherAdapter = WeatherAdapter()

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
        setupWeatherViewPager()
        setupOnRefreshListener()
    }

    private fun setupWeatherViewPager() {
        binding.weatherViewPager.apply {
            adapter = weatherAdapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    weatherViewModel.setLastSelectedDay(position)
                }
            })
        }
    }

    private fun setupOnRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            weatherViewModel.getUpToDateWeatherForecast()
        }
    }

    private fun observeDataChanges() {
        weatherViewModel.apply {
            isRefreshing.observe(viewLifecycleOwner) { status ->
                binding.swipeRefreshLayout.isRefreshing = status
            }

            weatherList.observe(viewLifecycleOwner) { list ->
                weatherAdapter.submitList(list)
                binding.weatherViewPager.setCurrentItem(lastSelectedDay, false)
            }

            weatherLoadingResultMessage.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let { message ->
                    SnackBarBuilder.show(requireView(), message)
                }
            }
        }
    }

    companion object {
        fun newInstance() = WeatherContainerFragment()
    }
}