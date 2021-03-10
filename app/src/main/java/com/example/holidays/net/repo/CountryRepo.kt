package com.example.holidays.net.repo

import com.example.holidays.net.responses.Country
import com.example.holidays.net.services.CountryService
import io.reactivex.Observable

class CountryRepo(private val api: CountryService) {

    fun getCountries(): Observable<MutableList<Country>> = api.getCountries().map {
        it.response?.countries
    }
}