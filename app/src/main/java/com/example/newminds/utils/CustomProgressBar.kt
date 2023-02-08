package com.example.newminds.utils

import kotlin.jvm.JvmOverloads
import com.example.newminds.R
import androidx.annotation.ColorInt
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator

class CustomProgressBar : View {
    private var foregroundProgressThickness = 10
    private var backgroundProgressThickness = 5
    private val foregroundCircle = Paint()
    private val backgroundCircle = Paint()
    private var progress = 0f
    private val mBarColorStandard = -0xff6978 //stylish blue
    private var foregroundProgressColor = Color.GREEN
    private var backgroundProgressColor = Color.GRAY
    private var foregroundBarGradientColors = intArrayOf(mBarColorStandard)
    private var backgroundBarGradientColors = intArrayOf(mBarColorStandard)
    private var min = 0
    var max = 100
    private val startAngle = -90
    private var h = 0
    private var w = 0
    private var centerPoint = 0
    private var subtractingValue = 0
    private var rectF = RectF()
    private var roundedCorner = false
    private var drawRadius = 0
    private var drawOuterRadius = 0
    private val DEFAULT_ANIMATION_DURATION = 2100

    constructor(context: Context?) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, 0, 0)
        val colorsArray = context.resources.obtainTypedArray(R.array.gradient_colors)
        backgroundProgressThickness = typedArray.getInteger(
            R.styleable.CustomProgressBar_background_progress_thickness,
            backgroundProgressThickness
        )
        foregroundProgressThickness = typedArray.getInteger(
            R.styleable.CustomProgressBar_foreground_progress_thickness,
            foregroundProgressThickness
        )
        progress = typedArray.getFloat(R.styleable.CustomProgressBar_progress, progress)
        foregroundProgressColor = typedArray.getInt(
            R.styleable.CustomProgressBar_foreground_progress_color,
            foregroundProgressColor
        )
        backgroundProgressColor = typedArray.getColor(
            R.styleable.CustomProgressBar_background_progress_color,
            backgroundProgressColor
        )
        roundedCorner =
            typedArray.getBoolean(R.styleable.CustomProgressBar_rounded_corner, roundedCorner)
        min = typedArray.getInt(R.styleable.CustomProgressBar_min, min)
        max = typedArray.getInt(R.styleable.CustomProgressBar_max, max)
        foregroundBarGradientColors = IntArray(colorsArray.length())
        for (i in 0 until colorsArray.length()) {
            foregroundBarGradientColors[i] = colorsArray.getColor(i, 0)
        }
        typedArray.recycle()
        init()
    }

    private fun init() {
        rectF = RectF()
        foregroundCircle.strokeWidth = foregroundProgressThickness.toFloat()
        foregroundCircle.isAntiAlias = true
        foregroundCircle.style = Paint.Style.STROKE
        backgroundCircle.isAntiAlias = true
        backgroundCircle.style = Paint.Style.STROKE
        backgroundCircle.strokeWidth = backgroundProgressThickness.toFloat()
        setRoundedCorner(roundedCorner)
        setupBarPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        w = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        h = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        centerPoint = Math.min(width, height)
        min = Math.min(width, height)
        setMeasuredDimension(min, min)
        setRadiusRect()
    }

    private fun setRadiusRect() {
        centerPoint = Math.min(width, height) / 2
        subtractingValue =
            if (backgroundProgressThickness > foregroundProgressThickness) backgroundProgressThickness else foregroundProgressThickness
        val newSeekWidth = subtractingValue / 2
        drawRadius = Math.min((width - subtractingValue) / 2, (height - subtractingValue) / 2)
        drawOuterRadius = Math.min(width - newSeekWidth, height - newSeekWidth)
        rectF[(subtractingValue / 2).toFloat(), (subtractingValue / 2).toFloat(), drawOuterRadius.toFloat()] =
            drawOuterRadius.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(
            centerPoint.toFloat(),
            centerPoint.toFloat(),
            drawRadius.toFloat(),
            backgroundCircle
        )
        val sweepAngle = 360 * progress / max
        canvas.drawArc(rectF, startAngle.toFloat(), sweepAngle, false, foregroundCircle)
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        this.invalidate()
    }

    fun setForegroundBarGradientColors(@ColorInt vararg foregroundBarGradientColors: Int) {
        this.foregroundBarGradientColors = foregroundBarGradientColors
        setupBarPaint()
    }

    fun setBackgroundBarGradientColors(@ColorInt vararg backgroundBarGradientColors: Int) {
        this.backgroundBarGradientColors = backgroundBarGradientColors
        setupBarPaint()
    }

    fun setBackgroundProgressThickness(thickness: Int) {
        backgroundProgressThickness = thickness
        backgroundCircle.strokeWidth = backgroundProgressThickness.toFloat()
        requestLayout()
        invalidate()
    }

    fun getBackgroundProgressThickness(): Int {
        return backgroundProgressThickness
    }

    fun getForegroundProgressThickness(): Int {
        return foregroundProgressThickness
    }

    fun setForegroundProgressThickness(foregroundProgressThickness: Int) {
        this.foregroundProgressThickness = foregroundProgressThickness
        foregroundCircle.strokeWidth = foregroundProgressThickness.toFloat()
        requestLayout()
        invalidate()
    }

    private fun setupBarPaint() {
        if (foregroundBarGradientColors.size > 1) {
            val matrix = Matrix()
            foregroundCircle.shader =
                SweepGradient(centerPoint.toFloat(), centerPoint.toFloat(), foregroundBarGradientColors, null)
            foregroundCircle.shader.getLocalMatrix(matrix)
            matrix.postTranslate(-centerPoint.toFloat(), -centerPoint.toFloat())
            matrix.postRotate(startAngle.toFloat())
            matrix.postTranslate(centerPoint.toFloat(), centerPoint.toFloat())
            foregroundCircle.shader.setLocalMatrix(matrix)
            foregroundCircle.color = foregroundBarGradientColors[0]
        } else if (foregroundBarGradientColors.size == 1) {
            foregroundCircle.color = foregroundProgressColor
            foregroundCircle.shader = null
        } else {
            foregroundCircle.color = foregroundProgressColor
            foregroundCircle.shader = null
        }
        if (backgroundBarGradientColors.size > 1) {
            val matrix = Matrix()
            backgroundCircle.shader =
                SweepGradient(centerPoint.toFloat(), centerPoint.toFloat(), backgroundBarGradientColors, null)
            backgroundCircle.shader.getLocalMatrix(matrix)
            matrix.postTranslate(-centerPoint.toFloat(), -centerPoint.toFloat())
            matrix.postRotate(startAngle.toFloat())
            matrix.postTranslate(centerPoint.toFloat(), centerPoint.toFloat())
            backgroundCircle.shader.setLocalMatrix(matrix)
            backgroundCircle.color = backgroundBarGradientColors[0]
        } else if (backgroundBarGradientColors.size == 1) {
            backgroundCircle.color = backgroundProgressColor
            backgroundCircle.shader = null
        } else {
            backgroundCircle.color = backgroundProgressColor
            backgroundCircle.shader = null
        }
    }

    fun setRoundedCorner(roundedCorner: Boolean) {
        if (roundedCorner) {
            foregroundCircle.strokeCap = Paint.Cap.ROUND
            backgroundCircle.strokeCap = Paint.Cap.ROUND
        } else {
            foregroundCircle.strokeCap = Paint.Cap.SQUARE
            backgroundCircle.strokeCap = Paint.Cap.SQUARE
        }
    }

    fun setProgressWithAnimation(progress: Float) {
        val objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress)
        objectAnimator.duration = DEFAULT_ANIMATION_DURATION.toLong()
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.start()
    }

    fun setProgressWithAnimation(progress: Float, duration: Int) {
        val objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress)
        objectAnimator.duration = duration.toLong()
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.start()
    }

    fun getForegroundProgressColor(): Int {
        return foregroundProgressColor
    }

    fun setForegroundProgressColor(foregroundProgressColor: Int) {
        this.foregroundProgressColor = foregroundProgressColor
        foregroundCircle.color = foregroundProgressColor
        invalidate()
    }

    fun getBackgroundProgressColor(): Int {
        return backgroundProgressColor
    }

    fun setBackgroundProgressColor(backgroundProgressColor: Int) {
        this.backgroundProgressColor = backgroundProgressColor
        backgroundCircle.color = backgroundProgressColor
        invalidate()
    }
}