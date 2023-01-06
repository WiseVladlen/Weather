package com.example.weather.utils.views.builders

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarBuilder {

    private const val MESSAGE_DISPLAY_TIME = 1500

    fun show(view: View, message: String, duration: Int = MESSAGE_DISPLAY_TIME) {
        Snackbar.make(view, message, duration).show()
    }
}