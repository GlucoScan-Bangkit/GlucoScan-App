package com.dicoding.glucoscan.ui.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.dicoding.glucoscan.R

/**
 * TODO: document your custom view class.
 */
class DailyRounded : View {

    private var _data: String? = null
    private var _color: Int = Color.RED
    private var _customBackgroundColor: Int = Color.LTGRAY
    private var _dimension: Float = 0f
    private var _type: String? = null

    private lateinit var textPaint: TextPaint
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * The text to draw
     */
    var data: String?
        get() = _data
        set(value) {
            _data = value
            updateBackgroundColor()
            updateTextColor()
            invalidateTextPaintAndMeasurements()
            invalidate()
        }

    var type: String?
        get() = _type
        set(value) {
            _type = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * The font color
     */
    var color: Int
        get() = _color
        set(value) {
            _color = value
            invalidateTextPaintAndMeasurements()
        }

    var customBackgroundColor: Int
        get() = _customBackgroundColor
        set(value) {
            _customBackgroundColor = value
            updateBackgroundColor()
            invalidateTextPaintAndMeasurements()
            invalidate()
        }

    var dimension: Float
        get() = _dimension
        set(value) {
            _dimension = value
            invalidateTextPaintAndMeasurements()
        }


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.DailyRounded, defStyle, 0)

        _data = a.getString(
                R.styleable.DailyRounded_data) ?: "0"
        _type = a.getString(
                R.styleable.DailyRounded_type)
        _color = a.getColor(
                R.styleable.DailyRounded_color,
                color)
        _customBackgroundColor = a.getColor(
                R.styleable.DailyRounded_backgroundColor,
                customBackgroundColor)
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        _dimension = a.getDimension(
                R.styleable.DailyRounded_dimension,
                dimension)

        a.recycle()

        // Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
        }
        updateBackgroundColor()
        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun updateBackgroundColor() {
        if (customBackgroundColor == Color.LTGRAY) {
            backgroundPaint.color = when (_data?.toIntOrNull()) {
                in 1..50 -> context.getColor(R.color.green_300)
                in 51..100 -> context.getColor(R.color.yellow_300)
                in 101..Int.MAX_VALUE -> context.getColor(R.color.red_300)
                else -> Color.LTGRAY
            }
        } else {
            backgroundPaint.color = customBackgroundColor
        }
    }

    private fun updateTextColor(){
        color = when (_data?.toIntOrNull()) {
            in 1..50 -> context.getColor(R.color.green_900)
            in 51..100 -> context.getColor(R.color.yellow_900)
            in 101..Int.MAX_VALUE -> context.getColor(R.color.red_900)
            else -> context.getColor(R.color.neutral_900)
        }
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint.let {
            it.textSize = dimension
            it.color = color
            textWidth = it.measureText(data)
            textHeight = it.fontMetrics.descent - it.fontMetrics.ascent
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom
        val centerX = paddingLeft + contentWidth / 2f
        val centerY = paddingTop + contentHeight / 2f
        val radius = Math.min(contentWidth, contentHeight) / 2f

        // Save the canvas state
        canvas.save()

        // Clip the canvas to a circular region
        val circularPath = Path().apply {
            addCircle(centerX, centerY, radius, Path.Direction.CW)
        }
        canvas.clipPath(circularPath)

        // Draw the circular background
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

        // Draw the text inside the circle, centered
        data?.let {
            val text = when(type){
                "sugar" -> "$it gr"
                else -> it
            }

            val textBound = Rect()
            textPaint.getTextBounds(text, 0, text.length, textBound)

            val textX = centerX - textBound.width() / 2f
            val textY = centerY + textBound.height() / 2f - textBound.bottom

            canvas.drawText(
                text,
                textX,
                textY,
                textPaint
            )
        }

        // Restore the canvas to its original state
        canvas.restore()
    }
}