package com.example.holidays.ui.startscreen.countriesadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.holidays.R
import com.example.holidays.net.responses.Country
import com.example.holidays.ui.base.BaseListAdapter
import com.example.holidays.ui.base.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CountriesAdapter (countries: ArrayList<Country> = arrayListOf()) :
    BaseListAdapter<Country>(countries) {

    private val itemClickSubject = PublishSubject.create<Country>()
    val itemClickObservable: Observable<Country> = itemClickSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Country> {
        return CountriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_country_details, parent, false),
            itemClickSubject
        )
    }
}