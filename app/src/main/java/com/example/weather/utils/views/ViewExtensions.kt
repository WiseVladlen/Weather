package com.example.weather.utils.views

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.MainThread
import androidx.appcompat.widget.SearchView

inline fun SearchView.onQueryTextChanged(crossinline textChangeListener: (String?) -> Unit) {

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            clearFocus()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            textChangeListener(newText)
            return true
        }
    })
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

@MainThread
fun View.hideSoftKeyboard() {
    if (requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

@MainThread
fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}