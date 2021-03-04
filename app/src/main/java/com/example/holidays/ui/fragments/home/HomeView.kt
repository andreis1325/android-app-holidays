package com.example.holidays.ui.fragments.home

import com.example.gallery_settings.ui.base.BaseMvpView
import com.example.holidays.net.responses.Country
import com.example.holidays.net.responses.Holiday
import com.example.holidays.net.responses.HolidayResponse
import java.util.ArrayList

interface HomeView : BaseMvpView {

    fun setCountryAndYear(country: String?, year: Int)
    fun showOrHideBottomSheetSelectCountry()
    fun updateCountries(countryList: ArrayList<Country>)
    fun setCountry(country: String?)
    fun showOrHideBottomSheetSelectYear()
    fun setYear(year: String)
    fun updateHolidays(holidayList: ArrayList<Holiday>)
    fun showModel(it: String)
    fun changeImageAndShowKeyboard()
    fun changeImageAndHideKeyboard()
}