package com.dicoding.glucoscan.ui.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.dicoding.glucoscan.R

/**
 * TODO: document your custom view class.
 */
class dailyRounded : View {

    private var _data: String? = null
    private var _color: Int = Color.RED
    private var _dimension: Float = 0f

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
                attrs, R.styleable.dailyRounded, defStyle, 0)

        _data = a.getString(
                R.styleable.dailyRounded_data)
        _color = a.getColor(
                R.styleable.dailyRounded_color,
                color)
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        _dimension = a.getDimension(
                R.styleable.dailyRounded_dimension,
                dimension)

        a.recycle()

        // Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
        }

        backgroundPaint.color = Color.LTGRAY

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint.let {
            it.textSize = dimension
            it.color = color
            textWidth = it.measureText(data)
            textHeight = it.fontMetrics.bottom
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom
        val centerX = width / 2
        val centerY = height / 2

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom
        val radius = Math.min(contentWidth, contentHeight) / 2f

        canvas.save()
        canvas.clipPath(Path().apply {
            addCircle(
                paddingLeft + contentWidth / 2f,
                paddingTop + contentHeight / 2f,
                radius,
                Path.Direction.CW
            )
        })

        canvas.drawCircle(
            paddingLeft + contentWidth / 2f,
            paddingTop + contentHeight / 2f,
            radius,
            backgroundPaint
        )

        data?.let {
            // Draw the text.
            canvas.drawText(it,
                    paddingLeft + (contentWidth - textWidth) / 2,
                    paddingTop + (contentHeight + textHeight) / 2,
                    textPaint)
        }

        canvas.restore()
    }
}