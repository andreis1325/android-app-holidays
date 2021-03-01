package com.example.holidays.ui.startscreen

import com.example.gallery_settings.ui.base.BaseMvpView

interface StartScreenView: BaseMvpView {

    fun showOrHideBottomSheetSelectCountry()
    fun setCountry(country: String)
    fun updateCountries(countryList: ArrayList<String>)
    fun showOrHideBottomSheetSelectYear()
    fun setYear(year: String)
    fun goToNavigationActivity()
}