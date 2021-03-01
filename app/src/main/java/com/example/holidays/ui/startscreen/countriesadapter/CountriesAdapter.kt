package com.example.holidays.ui.startscreen.countriesadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.holidays.R
import com.example.holidays.ui.base.BaseListAdapter
import com.example.holidays.ui.base.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CountriesAdapter (countries: ArrayList<String> = arrayListOf()) :
    BaseListAdapter<String>(countries) {

    private val itemClickSubject = PublishSubject.create<String>()
    val itemClickObservable: Observable<String> = itemClickSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<String> {
        return CountriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bottom_sheet, parent, false),
            itemClickSubject
        )
    }
}