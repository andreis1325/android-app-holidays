package com.delivery.utils.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.holidays.R

open class BottomSheetLayoutWithBackground @kotlin.jvm.JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var shadow: View
    private var bottomSheetLayout: BottomSheetLayout

    var onCollapseListener = {}
    private var wasFullyExpanded = false

    init {
        shadow = View(context)
        shadow.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
        shadow.alpha = 0f

        addView(shadow, 0)

        bottomSheetLayout = BottomSheetLayout(context, attrs)
        val layoutParams =
            FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
                gravity = Gravity.BOTTOM
            }

        bottomSheetLayout.layoutParams = layoutParams

        addView(bottomSheetLayout, 1)

        bottomSheetLayout.setOnProgressListener { progress ->
            var newAlpha = progress * 0.2f
            if (Math.abs(newAlpha - shadow.alpha) > 0.005 || newAlpha == 0f || newAlpha == 0.3f) {
//                Log.i("asdasdasd", "new alpha = $newAlpha")
                shadow.alpha = newAlpha
            }

            if(progress == 1f)
                wasFullyExpanded = true

            if(progress == 0f && wasFullyExpanded)
                onCollapseListener()
        }

        bottomSheetLayout.setOnClickListener {
            hide()
        }
    }

    class MyView @kotlin.jvm.JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : View(context, attrs, defStyleAttr) {

        override fun dispatchTouchEvent(event: MotionEvent?): Boolean {

            when (event!!.actionMasked) {
                MotionEvent.ACTION_DOWN -> Log.e("test", "ACTION_DOWN")
                MotionEvent.ACTION_UP -> Log.e("test", "ACTION_UP")
                MotionEvent.ACTION_CANCEL -> Log.e("test", "ACTION_CANCEL")
            }
            return super.dispatchTouchEvent(event)
        }
    }

    override fun onFinishInflate() {
//        Log.i("asdasdasd", "onfinishinflate")
        super.onFinishInflate()

        val view = getChildAt(2)
        removeViewAt(2)
        bottomSheetLayout.addView(view)

//        invalidate()
//        requestLayout()

        bottomSheetLayout.reinit()
    }

    fun toggle() {
        bottomSheetLayout.toggle()
    }

    fun expand() {
        if (bottomSheetLayout.isExpanded().not()) {
            bottomSheetLayout.expand()
        }
    }

    fun hide() {
        bottomSheetLayout.collapse()
    }

    fun isExpanded(): Boolean {
        return bottomSheetLayout.isExpanded()
    }
}