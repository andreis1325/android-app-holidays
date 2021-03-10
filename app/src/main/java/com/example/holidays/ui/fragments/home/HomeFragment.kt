package com.example.holidays.ui.fragments.home

import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.gallery_settings.ui.base.BaseMvpFragment
import com.example.holidays.utils.extensions.gone
import com.example.holidays.utils.extensions.visible
import com.example.holidays.R
import com.example.holidays.net.responses.Country
import com.example.holidays.net.responses.Holiday
import com.example.holidays.ui.fragments.home.holidayadapter.HolidayAdapter
import com.example.holidays.ui.startscreen.countriesadapter.CountriesAdapter
import com.example.holidays.utils.extended.SimpleTextWatcher
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_bottom_sheet.*
import kotlinx.android.synthetic.main.view_year_details.*

class HomeFragment : BaseMvpFragment(), HomeView {

    @InjectPresenter
    lateinit var homePresenter: HomePresenter

    private lateinit var countriesAdapter: CountriesAdapter
    private lateinit var holidayAdapter: HolidayAdapter

    companion object {

        private const val FRICTION = 0.02f

        fun newInstance(): HomeFragment {

            return HomeFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View) {

        initOnClickListener()
        initCountryAdapter()
        initHolidayAdapter()
        initNumberPicker()

        homePresenter.onCreate(countriesAdapter.itemClickObservable)
    }

    private fun initHolidayAdapter() {
        holidayAdapter = HolidayAdapter()
        vRvHolidays.adapter = holidayAdapter
    }

    private fun initCountryAdapter() {
        countriesAdapter = CountriesAdapter()
        vRvCountries.adapter = countriesAdapter
    }

    private fun initNumberPicker() {

        val min = resources.getInteger(R.integer.min_year)
        val max = resources.getInteger(R.integer.max_year)

        vNpvYears.apply {
            displayedValues = Array(max - min+1) { index -> (index+min).toString() }
            minValue = min
            maxValue = max
            setFriction(FRICTION)
        }
    }

    private fun initOnClickListener() {

        vIvSearch.setOnClickListener {
            homePresenter.onSearchClicked()
        }

        vIvCancel.setOnClickListener {
            homePresenter.onCancelClicked()
        }

        vTvCountry.setOnClickListener {
            homePresenter.onCountryClicked()
        }

        vTvYear.setOnClickListener {
            homePresenter.onYearClicked()
        }

        vEtCountrySearch?.addTextChangedListener(object : SimpleTextWatcher() {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                homePresenter.onTextChanged(s)
            }
        })

        vNpvYears.setOnValueChangedListener { _, _, newVal ->
            homePresenter.onNumberPickerValueChanged(newVal)
        }

        vIvBack.setOnClickListener {
            homePresenter.onBackClicked()
        }
    }

    override fun setCountryAndYear(country: String, year: Int) {
        vTvCountry.text = country
        vTvYear.text = year.toString()
    }

    override fun showOrHideBottomSheetSelectCountry() {
        vBsSelectCountry.toggle()
    }

    override fun updateCountries(countryList: MutableList<Country>) {
        countriesAdapter.setItems(countryList)
    }

    override fun setCountryAndCloseBottomSheet(country: String) {
        vBsSelectCountry.toggle()
        vEtCountrySearch.setText("")
        closeKeyboard()
        vTvCountry.text = country
        vEtCountrySearch.clearFocus()
    }

    override fun showOrHideBottomSheetSelectYear() {
        vBsSelectYear.toggle()
    }

    override fun setYear(year: String) {
        vTvYear.text = year
    }

    override fun updateHolidays(holidayList: MutableList<Holiday>) {
        holidayAdapter.setItems(holidayList)
    }

    override fun showModel(it: String) {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    override fun changeImageAndHideKeyboard() {
        vEtCountrySearch.setText("")

        vIvCancel.gone()
        vEtCountrySearch.gone()

        vIvSearch.visible()
        vTvCountrySearch.visible()

        closeKeyboard()
        vEtCountrySearch.clearFocus()
    }

    override fun changeImageAndShowKeyboard() {
        vIvSearch.gone()
        vTvCountrySearch.gone()

        vEtCountrySearch.visible()
        vIvCancel.visible()

        vEtCountrySearch.requestFocus()
        showKeyboard(vEtCountrySearch)
    }
}