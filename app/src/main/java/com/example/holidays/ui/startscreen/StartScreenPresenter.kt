package com.example.holidays.ui.startscreen

import com.arellomobile.mvp.InjectViewState
import com.example.holidays.MyApp
import com.example.holidays.net.repo.CountryRepo
import com.example.holidays.net.repo.SharedPreferencesRepo
import com.example.holidays.net.responses.Country
import com.example.holidays.ui.base.BaseMvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.instance
import java.util.*

@InjectViewState
class StartScreenPresenter : BaseMvpPresenter<StartScreenView>() {

    private val sharedPreferencesRepo by MyApp.kodein.instance<SharedPreferencesRepo>()
    private val countryRepo by MyApp.kodein.instance<CountryRepo>()

    private var countryList: MutableList<Country> = arrayListOf()
    private var isFilledCountry = false
    private var isFilledYear = false

    companion object {
        private const val FILL_INFO = "Fill info"
        private const val ERROR = "Error"
    }

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
        if (sharedPreferencesRepo.getYear() > 0)
            viewState.goToNavigationActivity()
    }

    private fun loadAndUpdateUI() {
        addDisposable(
            countryRepo.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    countryList = it
                    viewState.updateCountries(countryList)
                },
                    {
                        viewState.showMessage(ERROR)
                    }
                )
        )
    }

    private fun initOnCountryClickedListener(itemClickObservable: Observable<Country>) {
        isFilledCountry = true

        addDisposable(
            itemClickObservable.subscribe {

                sharedPreferencesRepo.setCountry(it.countryName)
                sharedPreferencesRepo.setIso(it.iso)
                viewState.setCountryAndCloseBottomSheet(it.countryName ?: "")
            }
        )
    }

    fun onTextChanged(text: CharSequence?) {

        viewState.updateCountries(countryList.filter { it ->
            it.countryName?.toLowerCase(Locale.ROOT)?.contains(text.toString(), false) ?: false
        } as MutableList<Country>)
    }

    fun onNumberPickerValueChanged(newVal: Int) {
        isFilledYear = true
        sharedPreferencesRepo.setYear(newVal)
        viewState.setYear(newVal.toString())
    }

    fun onBackClicked() {
        viewState.showOrHideBottomSheetSelectYear()
    }

    fun onNextClicked() {
        if (isFilledCountry && isFilledYear)
            viewState.goToNavigationActivity()
        else
            viewState.showMessage(FILL_INFO)
    }

    fun onCancelClicked() {
        viewState.changeImageAndHideKeyboard()
    }

    fun onSearchClicked() {
        viewState.changeImageAndShowKeyboard()
    }

}