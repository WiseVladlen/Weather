package com.example.weather.utils.views.builders

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.weather.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NoLocationsDialogBuilder(context: Context) : MaterialAlertDialogBuilder(context) {

    override fun create(): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setTitle(R.string.title_location_not_selected)
            .setMessage(R.string.subtitle_specify_at_least_one_location)
            .setPositiveButton(R.string.action_ok) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}