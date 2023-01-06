package com.example.weather.utils

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController

fun Fragment.requireNavController(): NavController {
    return requireView().findNavController()
}

@MainThread
fun NavController.navigateSafely(directions: NavDirections) {
    currentDestination?.getAction(directions.actionId)?.let { navigate(directions) }
}