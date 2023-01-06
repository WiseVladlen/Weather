package com.example.weather.utils.views.builders

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.weather.R
import com.example.weather.domain.entities.Location
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteLocationDialogBuilder(
    context: Context,
    private val location: Location,
    private val listener: OnDeleteButtonClickListener,
) : MaterialAlertDialogBuilder(context) {

    override fun create(): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setTitle(location.name)
            .setMessage(R.string.subtitle_deleting_location)
            .setNegativeButton(R.string.action_cancel) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(R.string.action_delete) { dialog, _ ->
                dialog.dismiss()
                listener.onClick()
            }
            .show()
    }

    fun interface OnDeleteButtonClickListener {
        fun onClick()
    }
}