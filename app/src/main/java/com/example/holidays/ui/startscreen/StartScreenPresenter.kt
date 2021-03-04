package com.example.holidays.ui.startscreen

import android.content.SharedPreferences
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.example.holidays.MyApp
import com.example.holidays.model.country
import com.example.holidays.model.iso
import com.example.holidays.model.year
import com.example.holidays.net.repo.CountryRepo
import com.example.holidays.net.responses.Country
import com.example.holidays.ui.base.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.instance

@InjectViewState
class StartScreenPresenter : BaseMvpPresenter<StartScreenView>() {

    private val sharedPreferences by MyApp.kodein.instance<SharedPreferences>()
    private val countryRepo by MyApp.kodein.instance<CountryRepo>()

    private  var countryList: ArrayList<Country> = arrayListOf()
    private var isFilledCountry = false
    private var isFilledYear = false

    fun onSelectCountryClicked() {
        viewState.showOrHideBottomSheetSelectCountry()

    }

    fun onSelectYearClicked() {
        viewState.showOrHideBottomSheetSelectYear()
    }

    fun onCreate(itemClickObservable: Observable<Country>) {
        showStartScreenOrGoToHolidays()
        loadAndUpdateUI()
        initOnCountryClickedListener(itemClickObservable)
    }

    private fun showStartScreenOrGoToHolidays() {
        if(sharedPreferences.year > 0)
            viewState.goToNavigationActivity()
    }

    private fun loadAndUpdateUI() {
        addDisposable(
            countryRepo.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    countryList=it
                    viewState.updateCountries(countryList)
                    },
                    {
                        viewState.showMessage("error")
                    }
                )
        )
    }

    private fun initOnCountryClickedListener(itemClickObservable: Observable<Country>) {
        isFilledCountry = true

        addDisposable(
            itemClickObservable.subscribe {
                sharedPreferences.country = it.countryName
                sharedPreferences.iso = it.iso
                viewState.setCountry(it.countryName)
            }
        )
    }

    fun onTextChanged(text: CharSequence?) {
        val searchText = text.toString()
        val searchedCountries: ArrayList<Country> = arrayListOf()

        for (item in countryList) {

            if(item.countryName != null){
                if (item.countryName.contains(searchText, true))
                    searchedCountries.add(item)
            }
        }
        viewState.updateCountries(searchedCountries)
    }

    fun onNumberPickerValueChanged(newVal: Int) {
        isFilledYear = true
        sharedPreferences.year = newVal
        viewState.setYear(newVal.toString())
    }

    fun onBackClicked() {
        viewState.showOrHideBottomSheetSelectYear()
    }

    fun onNextClicked() {
        if( isFilledCountry && isFilledYear)
            viewState.goToNavigationActivity()
        else
            viewState.showMessage("Fill info!")
    }

    fun onCancelClicked() {
        viewState.changeImageAndHideKeyboard()
    }

    fun onSearchClicked() {
        viewState.changeImageAndShowKeyboard()
    }

}