package com.example.weather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isNetworkAvailable(): Boolean {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
        getNetworkCapabilities(activeNetwork)?.run {
            when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                else -> false
            }
        } ?: false
    }
}