package com.example.holidays.ui.startscreen

import android.view.VerifiedInputEvent
import com.arellomobile.mvp.InjectViewState
import com.example.holidays.ui.base.BaseMvpPresenter
import io.reactivex.Observable

@InjectViewState
class StartScreenPresenter : BaseMvpPresenter<StartScreenView>() {

    private lateinit var countryList: ArrayList<String>

    fun onSelectCountryClicked() {
        viewState.showOrHideBottomSheetSelectCountry()

    }

    fun onSelectYearClicked() {
        viewState.showOrHideBottomSheetSelectYear()
    }

    fun onCreate(itemClickObservable: Observable<String>) {
        initOnCountryClickedListener(itemClickObservable)
        loadAndUpdateUI()
    }

    private fun loadAndUpdateUI() {
        countryList = arrayListOf("Belarus", "Russia", "Turkey", "Poland")

        viewState.updateCountries(countryList)
    }

    private fun initOnCountryClickedListener(itemClickObservable: Observable<String>) {
        addDisposable(
            itemClickObservable.subscribe {
                viewState.setCountry(it)
            }
        )
    }

    fun onTextChanged(text: CharSequence?) {
        val searchText = text.toString()
        val searchedCountries: ArrayList<String> = arrayListOf()

        for (item in countryList) {

            if (item.contains(searchText, true))
                searchedCountries.add(item)
        }
        viewState.updateCountries(searchedCountries)
    }

    fun onNumberPickerValueChanged(newVal: Int) {
        viewState.setYear(newVal.toString())
    }

    fun onBackClicked() {
        viewState.showOrHideBottomSheetSelectYear()
    }

    fun onNextClicked() {
        viewState.goToNavigationActivity()
    }
}