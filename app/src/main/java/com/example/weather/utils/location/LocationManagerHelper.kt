package com.example.weather.utils.location

import android.app.Activity
import android.content.pm.PackageManager
import android.Manifest.permission.*
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log

private val TAG = LocationManagerHelper::class.java.simpleName

object LocationManagerHelper {

    private const val REQUEST_CODE_LOCATION_PERMISSIONS = 200

    private const val MIN_TIME_MS = 0L
    private const val MIN_DISTANCE_M = 10f

    private val PERMISSIONS = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)

    private val PROVIDERS = arrayOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)

    fun Context.isLocationPermissionsGranted(): Boolean {
        val coarseLocationPermission = checkSelfPermission(ACCESS_COARSE_LOCATION)
        val fineLocationPermission = checkSelfPermission(ACCESS_FINE_LOCATION)

        return !(coarseLocationPermission != PackageManager.PERMISSION_GRANTED && fineLocationPermission != PackageManager.PERMISSION_GRANTED)
    }

    fun Activity.requestLocationPermissions() {
        requestPermissions(PERMISSIONS, REQUEST_CODE_LOCATION_PERMISSIONS)
    }

    fun LocationManager.requestLocationUpdates(locationListener: LocationListener) {
        try {
            for (provider in PROVIDERS) {
                requestLocationUpdates(
                    provider,
                    MIN_TIME_MS,
                    MIN_DISTANCE_M,
                    locationListener,
                )
            }
        } catch (ex: SecurityException) {
            ex.message?.let { Log.d(TAG, it) }
        } catch (ex: Exception) {
            ex.message?.let { Log.d(TAG, it) }
        }
    }
}