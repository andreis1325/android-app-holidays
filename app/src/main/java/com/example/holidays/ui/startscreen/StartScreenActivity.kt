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
import com.example.holidays.R
import com.example.holidays.net.responses.Country
import com.example.holidays.ui.navigation.NavigationActivity
import com.example.holidays.ui.startscreen.countriesadapter.CountriesAdapter
import com.example.holidays.utils.extended.SimpleTextWatcher
import com.example.holidays.utils.extensions.*
import kotlinx.android.synthetic.main.activity_start_screen.*
import kotlinx.android.synthetic.main.view_bottom_sheet.*
import kotlinx.android.synthetic.main.view_year_details.*

class StartScreenActivity : BaseMvpActivity(), StartScreenView {

    @InjectPresenter
    lateinit var startScreenPresenter: StartScreenPresenter

    private lateinit var countriesAdapter: CountriesAdapter

    companion object{
        private const val FRICTION = 0.02f
    }

    override fun getLayoutId(): Int = R.layout.activity_start_screen

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        initAdapter()
        startScreenPresenter.onCreate(countriesAdapter.itemClickObservable)
        setSpannableForSelectCountry()
        setSpannableForSelectYear()
        initNumberPicker()
        initOnClickListeners()
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

    private fun initOnClickListeners() {

        vIvSearch.setOnClickListener {
            startScreenPresenter.onSearchClicked()
        }

        vIvCancel.setOnClickListener {
            startScreenPresenter.onCancelClicked()
        }

        vEtCountrySearch?.addTextChangedListener(object : SimpleTextWatcher() {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                startScreenPresenter.onTextChanged(s)
            }
        })

        vNpvYears.setOnValueChangedListener { _, _, newVal ->

            startScreenPresenter.onNumberPickerValueChanged(newVal)
        }

        vIvBack.setOnClickListener {
            startScreenPresenter.onBackClicked()
        }

        vTvNext.setOnClickListener {
            startScreenPresenter.onNextClicked()
        }
    }

    private fun initAdapter() {
        countriesAdapter = CountriesAdapter()
        vRvCountries.adapter = countriesAdapter
    }

    private fun setSpannableForSelectYear(year: String = "") {
        var yearTitle: String = getString(R.string.select_year)

        if (year != "")
            yearTitle = yearTitle.substring(0, 6) + year

        val spannableStringBuilder =
            SpannableStringBuilder(yearTitle)
                .setBold(assets, 0, 4)
                .setClickable(onSelectYearClick(), 6)
                .setColor(ContextCompat.getColor(this, R.color.purple))

        vTvSelectYear.apply {
            text = spannableStringBuilder
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

    private fun setSpannableForSelectCountry(country: String = "") {
        var title: String = getString(R.string.select_country)

        if (country != "")
            title = title.substring(0, 9) + country

        val spannableStringBuilder =
            SpannableStringBuilder(title)
                .setBold(assets, 0, 8)
                .setClickable(onSelectCountryClick(), 8)
                .setColor(ContextCompat.getColor(this, R.color.purple))

        vTvSelectCountry.apply {
            text = spannableStringBuilder
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

    override fun setCountryAndCloseBottomSheet(country: String) {
        vBottomSheetSelectCountry.toggle()
        vEtCountrySearch.setText("")
        closeKeyboard()
        setSpannableForSelectCountry(country)
        vEtCountrySearch.clearFocus()
    }

    override fun updateCountries(countryList: MutableList<Country>) {
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
        finish()
    }

    override fun changeImageAndHideKeyboard() {
        vIvCancel.gone()
        vIvSearch.visible()

        closeKeyboard()
        vEtCountrySearch.clearFocus()
    }

    override fun changeImageAndShowKeyboard() {

        vIvSearch.gone()
        vTvCountrySearch.gone()

        vIvCancel.visible()
        vEtCountrySearch.visible()

        vEtCountrySearch.requestFocus()
        showSoftKeyboard(this.currentFocus)
    }

    override fun changeImages() {
        vIvSearch.gone()
        vIvCancel.visible()
    }
}