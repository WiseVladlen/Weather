package com.example.weather.utils

import android.content.Context
import android.content.Intent
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.weather.R
import com.example.weather.presentation.WeatherApplication
import com.example.weather.presentation.location_access.RequestLocationAccessFragment
import com.example.weather.presentation.weather.WeatherContainerFragment

private val TAG = WeatherApplication.INSTANCE.getString(R.string.tag_container)

@MainThread
fun Fragment.setupWeatherContainerFragment() {
    setupFragment(WeatherContainerFragment.newInstance())
}

@MainThread
fun Fragment.setupRequestLocationAccessFragment() {
    setupFragment(RequestLocationAccessFragment.newInstance())
}

@MainThread
fun Fragment.clearFragmentContainer() {
    childFragmentManager.findFragmentByTag(TAG).let { fragment ->
        if (fragment != null) {
            childFragmentManager.commit {
                remove(fragment)
                setReorderingAllowed(true)
            }
        }
    }
}

@MainThread
fun Context.shareMessage(text: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(this, shareIntent, null)
}

@MainThread
private fun Fragment.setupFragment(newFragment: Fragment) {
    childFragmentManager.findFragmentByTag(TAG).let { fragment ->
        if (fragment == null) {
            childFragmentManager.commit {
                add(R.id.fragment_container, newFragment, TAG)
                setReorderingAllowed(true)
            }
        } else {
            childFragmentManager.commit {
                replace(R.id.fragment_container, newFragment, TAG)
                setReorderingAllowed(true)
            }
        }
        return@let
    }
}