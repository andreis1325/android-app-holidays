package com.example.holidays.ui.fragments.home.holidayadapter

import android.view.View
import androidx.core.content.ContextCompat
import com.example.gallery_settings.utils.extensions.toDateFormat
import com.example.holidays.R
import com.example.holidays.net.responses.Date
import com.example.holidays.net.responses.Holiday
import com.example.holidays.ui.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_holiday_details.view.*
import java.text.SimpleDateFormat
import java.util.*

class HolidayViewHolder(
    itemView: View,
    var itemClickSubject: PublishSubject<Holiday>
) : BaseViewHolder<Holiday>(itemView) {

    override fun bind(model: Holiday) {
        setModel(model)
    }

    private fun setModel(model: Holiday) {
        setName(model.name ?: "")
        setDescription(model.description)
        setDate(model.date)
        setPicture(model.date?.iso)
    }

    private fun setPicture(iso: String?) {

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(iso)
        val date1 = Calendar.getInstance().time

        if (date1.compareTo(sdf) == 1) {
            itemView.vFlTime.setBackgroundResource(R.drawable.bg_green_circle)
            itemView.vIvTime.setBackgroundResource(R.drawable.ic_check)
        } else {
            itemView.vFlTime.setBackgroundResource(R.drawable.bg_yellow_circle)
            itemView.vIvTime.setBackgroundResource(R.drawable.ic_clock)
        }
    }

    private fun setDate(date: Date?) {
        itemView.vTvHolidayDate.text = date?.iso.toDateFormat()
    }

    private fun setDescription(description: String?) {
        itemView.vTvHolidayDescription.text = description ?: ""
    }

    private fun setName(name: String) {
        if (name.length > 30)
            itemView.vTvHolidayName.text = name.substring(0, 27) + "..."
        else
            itemView.vTvHolidayName.text = name
    }
}