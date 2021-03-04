package com.example.holidays.net.repo

import com.example.holidays.net.responses.Holiday
import com.example.holidays.net.services.HolidayService
import io.reactivex.Observable

class HolidayRepo(private val api: HolidayService) {

    fun getHolidays(country: String, year: Int ): Observable<ArrayList<Holiday>> = api.getHolidays(country, year).map {
        it.response?.holidays
    }
}