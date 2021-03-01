package com.example.holidays.ui.startscreen.countriesadapter

import android.view.View
import com.example.holidays.ui.base.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_bottom_sheet.view.*

class CountriesViewHolder(
    itemView: View,
    var itemClickSubject: PublishSubject<String>
) : BaseViewHolder<String>(itemView) {

    private fun initClickListener(country: String) {
        itemView.vTvTitle.setOnClickListener {
            itemClickSubject.onNext(country)
        }
    }

    override fun bind(model: String) {
        setText(model)
        initClickListener(model)
    }

    private fun setText(country: String) {
        itemView.vTvTitle.text = country
    }
}