package com.example.holidays.ui.startscreen

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.gallery_settings.ui.base.BaseMvpActivity
import com.example.gallery_settings.utils.extensions.setBold
import com.example.gallery_settings.utils.extensions.setClickable
import com.example.gallery_settings.utils.extensions.setColor
import com.example.holidays.R
import com.example.holidays.ui.navigation.NavigationActivity
import com.example.holidays.ui.startscreen.countriesadapter.CountriesAdapter
import com.example.holidays.utils.extended.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_start_screen.*
import kotlinx.android.synthetic.main.view_country_details.*
import kotlinx.android.synthetic.main.view_year_details.*

class StartScreenActivity : BaseMvpActivity(), StartScreenView {

    @InjectPresenter
    lateinit var startScreenPresenter: StartScreenPresenter

    private lateinit var countriesAdapter: CountriesAdapter

    override fun getLayoutId(): Int = R.layout.activity_start_screen

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        initAdapter()
        setSpannables()
        initNumberPicker()
        initOnClickListeners()
        startScreenPresenter.onCreate(countriesAdapter.itemClickObservable)

    }

    private fun initNumberPicker() {
        vNpYears.apply {
            minValue = 1990
            maxValue = 2021
            wrapSelectorWheel = false
        }
    }

    private fun initOnClickListeners() {

        vEtCountrySearch?.addTextChangedListener(object : SimpleTextWatcher() {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                startScreenPresenter.onTextChanged(s)
            }
        })

        vNpYears.setOnValueChangedListener { picker, oldVal, newVal ->
            startScreenPresenter.onNumberPickerValueChanged(newVal)
        }

        vIvBack.setOnClickListener{
            startScreenPresenter.onBackClicked()
        }

        vTvNext.setOnClickListener{
            startScreenPresenter.onNextClicked()
        }
    }

    private fun setSpannables() {
        setSpannableForSelectCountry()
        setSpannableForSelectYear()
    }

    private fun initAdapter() {
        countriesAdapter = CountriesAdapter()
        vRvCountries.adapter = countriesAdapter
    }

    private fun setSpannableForSelectYear(year: String = "") {
        var yearTitle: String = getString(R.string.select_year)

        if(year != "")
            yearTitle = yearTitle.substring(0, 6) + year

        val spannableStringBuilder =
            SpannableStringBuilder(yearTitle)
                .setBold(assets,0, 4 )
                .setClickable(onSelectYearClick(), 6)
                .setColor(ContextCompat.getColor(this, R.color.purple))

        vTvSelectYear.apply{
            text  = spannableStringBuilder
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }
    }

    private fun onSelectYearClick(): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                startScreenPresenter.onSelectYearClicked()
            }
        }
    }

    private fun setSpannableForSelectCountry(country: String="") {
        var title: String = getString(R.string.select_country)

        if(country!="")
            title = title.substring(0, 9) + country

        val spannableStringBuilder =
            SpannableStringBuilder(title)
                .setBold(assets,0, 8 )
                .setClickable(onSelectCountryClick(), 8)
                .setColor(ContextCompat.getColor(this, R.color.purple))

        vTvSelectCountry.apply{
            text  = spannableStringBuilder
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }
    }

    private fun onSelectCountryClick(): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                startScreenPresenter.onSelectCountryClicked()
            }
        }
    }

    override fun showOrHideBottomSheetSelectCountry() {
        vBottomSheetSelectCountry.toggle()
    }

    override fun setCountry(country: String) {
        vBottomSheetSelectCountry.toggle()
        vEtCountrySearch.setText("")
        closeKeyboard()
        setSpannableForSelectCountry(country)
    }

    override fun updateCountries(countryList: ArrayList<String>) {
        countriesAdapter.setItems(countryList)
    }

    override fun showOrHideBottomSheetSelectYear() {
        vBottomSheetSelectYear.toggle()
    }

    override fun setYear(year: String) {
        setSpannableForSelectYear(year)
    }

    override fun goToNavigationActivity() {
        startActivity(Intent(this, NavigationActivity::class.java))
    }
}