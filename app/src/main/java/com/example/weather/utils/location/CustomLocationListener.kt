package com.example.weather.utils.location

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

interface CustomLocationListener: LocationListener {

    override fun onLocationChanged(location: Location) { }

    override fun onLocationChanged(locations: MutableList<Location>) { }

    override fun onFlushComplete(requestCode: Int) { }

    @Deprecated("Deprecated in Java")
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { }

    override fun onProviderEnabled(provider: String) { }

    override fun onProviderDisabled(provider: String) { }
}