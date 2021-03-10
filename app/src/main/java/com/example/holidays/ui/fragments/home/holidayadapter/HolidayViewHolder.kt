package com.example.holidays.ui.fragments.home.holidayadapter

import android.view.View
import com.bumptech.glide.Glide
import com.example.holidays.utils.extensions.toDateFormat
import com.example.holidays.R
import com.example.holidays.net.responses.Date
import com.example.holidays.net.responses.Holiday
import com.example.holidays.ui.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_holiday_details.view.*
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.*

class HolidayViewHolder(
    itemView: View,
    var itemClickSubject: PublishSubject<Holiday>
) : BaseViewHolder<Holiday>(itemView) {

    companion object {
        var DATE_FORMAT = "yyyy-MM-dd"
    }

    override fun bind(model: Holiday) {
        setName(model.name ?: "")
        setDescription(model.description)
        setDate(model.date)
        setPicture(model.date?.iso ?: "")
    }

    private fun setPicture(iso: String) {

        val modelDate = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(iso)
        val currentDate =
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(LocalDate.now().toString())

        if (modelDate?.time ?: 0 < currentDate?.time ?: 0) {
            itemView.vFlTime.setBackgroundResource(R.drawable.bg_green_circle)

            Glide.with(context)
                .load(R.drawable.ic_check)
                .centerCrop()
                .into(itemView.vIvTime)

        } else {
            itemView.vFlTime.setBackgroundResource(R.drawable.bg_yellow_circle)

            Glide.with(context)
                .load(R.drawable.ic_clock)
                .centerCrop()
                .into(itemView.vIvTime)
        }
    }

    private fun setDate(date: Date?) {
        itemView.vTvHolidayDate.text = date?.iso.toDateFormat()
    }

    private fun setDescription(description: String?) {
        itemView.vTvHolidayDescription.text = description ?: ""
    }

    private fun setName(name: String) {
            itemView.vTvHolidayName.text = name
    }
}