package com.example.weather.utils.views.overrided_layouts

import android.content.Context
import android.util.AttributeSet
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.MotionEvent
import kotlin.math.abs

class MultiSwipeRefreshLayout : SwipeRefreshLayout {

    private var touchSlop = 0
    private var prevX = 0f
    private var prevY = 0f

    constructor(ctx: Context): super(ctx)
    constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr) {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                prevX = motionEvent.x
                prevY = motionEvent.y
            }
            MotionEvent.ACTION_MOVE -> {
                val evX = motionEvent.x
                val evy = motionEvent.y
                val xDiff = abs(evX - prevX)
                val yDiff = abs(evy - prevY)
                if (xDiff > touchSlop && xDiff > yDiff) {
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(motionEvent)
    }
}