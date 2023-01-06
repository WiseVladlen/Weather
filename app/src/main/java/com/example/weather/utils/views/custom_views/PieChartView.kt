package com.example.weather.utils.views.custom_views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.example.weather.R

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = DEF_STYLE_ATTR,
    defStyleRes: Int = DEF_STYLE_RES,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect: RectF = RectF(0f, 0f, 0f, 0f)

    fun setAngles(endAngle: Float, sweepAngle: Float) {
        this.endAngle = endAngle
        this.sweepAngle = sweepAngle
    }

    private var endAngle: Float = 0f

    private var sweepAngle: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    @ColorInt
    private var circleColor: Int = context.getColor(R.color.light_blue)
        set(value) {
            field = value
            backgroundPaint.color = value
            invalidate()
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PieChartView,
            DEF_STYLE_ATTR,
            DEF_STYLE_RES,
        ).apply {
            try {
                circleColor = getColor(
                    R.styleable.PieChartView_circleColor,
                    context.getColor(R.color.light_blue),
                )
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val centerX = width / 2f
        val centerY = height / 2f

        val leastValue = centerX.coerceAtMost(centerY).toInt()

        val measureWidth = resolveSize(leastValue, widthMeasureSpec)
        val measureHeight = resolveSize(leastValue, heightMeasureSpec)

        setMeasuredDimension(measureWidth, measureHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawArc(rect, START_ANGLE, sweepAngle, true, backgroundPaint)
    }

    fun animateView() {
        ValueAnimator.ofFloat(0f, endAngle).apply {
            duration = DURATION_ANIMATION
            addUpdateListener { animator ->
                sweepAngle = animator.animatedValue as Float
            }
            start()
        }
    }

    companion object {
        private const val DEF_STYLE_ATTR = 0
        private const val DEF_STYLE_RES = 0
        private const val DURATION_ANIMATION = 2000L
        private const val START_ANGLE = 90f
    }
}