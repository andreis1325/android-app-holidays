package com.example.holidays.ui.fragments.home

import android.content.SharedPreferences
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.holidays.MyApp
import com.example.holidays.model.country
import com.example.holidays.model.year
import com.example.holidays.net.repo.CountryRepo
import com.example.holidays.net.repo.HolidayRepo
import com.example.holidays.net.repo.SharedPreferencesRepo
import com.example.holidays.net.responses.Country
import com.example.holidays.ui.base.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.instance
import java.util.*

@InjectViewState
class HomePresenter : BaseMvpPresenter<HomeView>() {

    private val holidayRepo by MyApp.kodein.instance<HolidayRepo>()
    private val countryRepo by MyApp.kodein.instance<CountryRepo>()
    private val sharedPreferencesRepo by MyApp.kodein.instance<SharedPreferencesRepo>()
    private val sharedPreferences by MyApp.kodein.instance<SharedPreferences>()

    companion object {
        private const val LOG_ERROR = "ERROR"
    }

    private var countryList: MutableList<Country> = arrayListOf()

    fun onCreate(itemClickObservable: Observable<Country>) {
        setCountryAndYear()
        loadAndUpdateHolidayList()
        loadAndUpdateCountryList()

        initOnClickListener(itemClickObservable)
    }

    private fun setCountryAndYear() {
        viewState.setCountryAndYear(
            sharedPreferencesRepo.getCountry(),
            sharedPreferencesRepo.getYear()
        )
    }

    private fun loadAndUpdateCountryList() {
        addDisposable(
            countryRepo.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    countryList = it
                    viewState.updateCountries(countryList)
                },
                    {
                        Log.e(LOG_ERROR, it.toString())
                    }
                )
        )
    }

    private fun initOnClickListener(itemClickObservable: Observable<Country>) {
        addDisposable(
            itemClickObservable.subscribe {
                sharedPreferencesRepo.setCountry(it.countryName)
                sharedPreferencesRepo.setIso(it.iso)
                viewState.setCountryAndCloseBottomSheet(it.countryName ?: "")
                loadAndUpdateHolidayList()
            }
        )
    }

    private fun loadAndUpdateHolidayList() {

        addDisposable(
            holidayRepo.getHolidays(
                sharedPreferencesRepo.getIso(),
                sharedPreferencesRepo.getYear()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    viewState.updateHolidays(it)
                }, {
                    Log.e(LOG_ERROR, it.toString())
                })
        )
    }

    fun onCountryClicked() {
        viewState.showOrHideBottomSheetSelectCountry()
    }

    fun onTextChanged(text: CharSequence?) {

        viewState.updateCountries(countryList.filter { it ->
            it.countryName?.toLowerCase(Locale.ROOT)?.contains(text.toString(), false) ?: false
        } as MutableList<Country>)
    }

    fun onYearClicked() {
        viewState.showOrHideBottomSheetSelectYear()
    }

    fun onNumberPickerValueChanged(newVal: Int) {
        sharedPreferencesRepo.setYear(newVal)
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