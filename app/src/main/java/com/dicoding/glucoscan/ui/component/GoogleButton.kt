package com.dicoding.glucoscan.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.dicoding.glucoscan.R

@SuppressLint("UseCompatLoadingForDrawables")
class GoogleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.buttonStyle
) : LinearLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val textView: TextView

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        background = context.getDrawable(R.drawable.google_button_background)

        // Initialize and set up ImageView
        imageView = ImageView(context).apply {
            layoutParams = LayoutParams(
                (24 * resources.displayMetrics.density).toInt(),
                (24 * resources.displayMetrics.density).toInt()
            )
            setImageResource(R.drawable.google_logo)
            contentDescription = context.getString(R.string.continue_google_button)
        }

        // Initialize and set up TextView
        textView = TextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            text = context.getString(R.string.continue_google_button)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(ContextCompat.getColor(context, R.color.blue_500))
            setPadding(8, 0, 0, 0) // Padding to the left of the text
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        // Add ImageView and TextView to the LinearLayout
        addView(imageView)
        addView(textView)
    }
}
