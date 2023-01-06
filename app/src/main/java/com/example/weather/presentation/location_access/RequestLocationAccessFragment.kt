package com.example.weather.presentation.location_access

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weather.R
import com.example.weather.databinding.FragmentRequestLocationAccessBinding
import com.example.weather.utils.LOCATION_ACCESS_BUNDLE_KEY
import com.example.weather.utils.LOCATION_ACCESS_REQUEST_KEY
import com.example.weather.utils.location.LocationManagerHelper.isLocationPermissionsGranted
import com.example.weather.utils.location.LocationManagerHelper.requestLocationPermissions

class RequestLocationAccessFragment : Fragment(R.layout.fragment_request_location_access) {

    private val binding by viewBinding(FragmentRequestLocationAccessBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListener()
    }

    override fun onResume() {
        super.onResume()

        setFragmentResult(
            LOCATION_ACCESS_REQUEST_KEY,
            bundleOf(LOCATION_ACCESS_BUNDLE_KEY to requireContext().isLocationPermissionsGranted()),
        )
    }

    private fun setupOnClickListener() {
        binding.buttonAllowAccess.setOnClickListener {
            requireActivity().requestLocationPermissions()
        }
    }

    companion object {
        fun newInstance() = RequestLocationAccessFragment()
    }
}