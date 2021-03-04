package com.example.holidays.ui.fragments.home

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.example.holidays.MyApp
import com.example.holidays.model.country
import com.example.holidays.model.iso
import com.example.holidays.model.year
import com.example.holidays.net.repo.CountryRepo
import com.example.holidays.net.repo.HolidayRepo
import com.example.holidays.net.responses.Country
import com.example.holidays.net.responses.HolidayResponse
import com.example.holidays.ui.base.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.instance

@InjectViewState
class HomePresenter : BaseMvpPresenter<HomeView>() {

    private val sharedPreferences by MyApp.kodein.instance<SharedPreferences>()
    private val holidayRepo by MyApp.kodein.instance<HolidayRepo>()
    private val countryRepo by MyApp.kodein.instance<CountryRepo>()

    private  var countryList: ArrayList<Country> = arrayListOf()

    fun onCreate(itemClickObservable: Observable<Country>) {
        viewState.setCountryAndYear(sharedPreferences.country, sharedPreferences.year)
        loadAndUpdateUI()
        initOnClickListener(itemClickObservable)
    }

    private fun initOnClickListener(itemClickObservable: Observable<Country>) {
        addDisposable(
            itemClickObservable.subscribe{
                sharedPreferences.country = it.countryName
                sharedPreferences.iso = it.iso
                viewState.setCountry(it.countryName)
                loadAndUpdateHolidayList()
            }
        )
    }

    private fun loadAndUpdateUI() {
        loadAndUpdateCountryList()
        loadAndUpdateHolidayList()
    }

    private fun loadAndUpdateCountryList() {
        addDisposable(
            countryRepo.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    countryList=it
                    viewState.updateCountries(countryList)
                },
                    {

                    }
                )
        )
    }

    private fun loadAndUpdateHolidayList() {

       addDisposable(
           holidayRepo.getHolidays(sharedPreferences.iso ?:"", sharedPreferences.year)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe({

               viewState.updateHolidays(it)
           }, {

           })
       )
    }

    fun onCountryClicked() {
        viewState.showOrHideBottomSheetSelectCountry()
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

    fun onYearClicked() {
        viewState.showOrHideBottomSheetSelectYear()
    }

    fun onNumberPickerValueChanged(newVal: Int) {
        sharedPreferences.year = newVal
        viewState.setYear(newVal.toString())
        loadAndUpdateHolidayList()
    }

    fun onBackClicked() {
        viewState.showOrHideBottomSheetSelectYear()
    }

    fun onSearchClicked() {
        viewState.changeImageAndShowKeyboard()
    }

    fun onCancelClicked() {
        viewState.changeImageAndHideKeyboard()
    }
}