package com.example.holidays.ui.startscreen

import com.example.gallery_settings.ui.base.BaseMvpView
import com.example.holidays.net.responses.Country

interface StartScreenView: BaseMvpView {

    fun showOrHideBottomSheetSelectCountry()
    fun setCountry(country: String?)
    fun updateCountries(countryList: ArrayList<Country>)
    fun showOrHideBottomSheetSelectYear()
    fun setYear(year: String)
    fun goToNavigationActivity()
    fun changeImageAndHideKeyboard()
    fun changeImageAndShowKeyboard()
    fun changeImages()
}