package com.example.holidays.utils.extensions

import android.content.res.AssetManager
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import com.example.holidays.utils.extended.CustomTypefaceSpan
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    if (this.visibility != View.VISIBLE)
        this.visibility = View.VISIBLE
}

fun View.gone() {
    if (this.visibility != View.GONE)
        this.visibility = View.GONE
}

fun SpannableStringBuilder.setBold(
    assetManager: AssetManager,
    start: Int = 0,
    end: Int = this.length
): SpannableStringBuilder {

    val fontBold =
        Typeface.createFromAsset(assetManager, "font/OpenSans-Bold.ttf")

    this.setSpan(
        CustomTypefaceSpan("", fontBold),
        start,
        end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return this
}

fun String?.toDateFormat(): String {

    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val date = formatter.parse(this ?: "") ?: ""
    return SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(date) ?: ""
}

fun SpannableStringBuilder.setClickable(
    clickableSpan: ClickableSpan,
    start: Int = 0,
    end: Int = this.length
): SpannableStringBuilder {
    this.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    val noUnderline = object : UnderlineSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
        }
    }
    this.setSpan(noUnderline, start, end, 0)

    return this
}

fun SpannableStringBuilder.setColor(
    color: Int,
    start: Int = 0,
    end: Int = this.length
): SpannableStringBuilder {

    val foregroundSpanSecond = ForegroundColorSpan(color)

    this.setSpan(
        foregroundSpanSecond,
        start,
        end,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return this
}


