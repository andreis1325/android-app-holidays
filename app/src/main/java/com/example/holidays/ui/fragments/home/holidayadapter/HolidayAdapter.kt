package com.example.holidays.ui.fragments.home.holidayadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.holidays.R
import com.example.holidays.net.responses.Holiday
import com.example.holidays.ui.base.BaseListAdapter
import com.example.holidays.ui.base.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HolidayAdapter(holidays: ArrayList<Holiday> = arrayListOf()) :
    BaseListAdapter<Holiday>(holidays) {

    private val itemClickSubject = PublishSubject.create<Holiday>()
    val itemClickObservable: Observable<Holiday> = itemClickSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Holiday> {
        return HolidayViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_holiday_details, parent, false),
            itemClickSubject
        )
    }
}