package com.example.holidays.ui.startscreen.countriesadapter

import android.view.View
import com.example.holidays.net.responses.Country
import com.example.holidays.ui.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_country_details.view.*

class CountriesViewHolder(
    itemView: View,
    var itemClickSubject: PublishSubject<Country>
) : BaseViewHolder<Country>(itemView) {

    private fun initClickListener(model: Country) {
        itemView.vTvTitle.setOnClickListener {
            itemClickSubject.onNext(model)
        }
    }

    override fun bind(model: Country) {
        setText(model.countryName)
        initClickListener(model)
    }

    private fun setText(country: String?) {
        itemView.vTvTitle.text = country ?: ""
    }
}