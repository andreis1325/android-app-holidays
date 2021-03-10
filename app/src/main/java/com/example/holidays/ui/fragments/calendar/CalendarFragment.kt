package com.example.holidays.ui.fragments.calendar

import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.gallery_settings.ui.base.BaseMvpFragment
import com.example.holidays.R

class CalendarFragment : BaseMvpFragment(), CalendarView {

    @InjectPresenter
    lateinit var calendarPresenter: CalendarPresenter

    companion object {
        fun newInstance(): CalendarFragment {
            return CalendarFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_calendar

    override fun onViewCreated(view: View) {

    }
}