package com.example.holidays.net.services

import com.example.holidays.net.responses.HolidayResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface CountryService {

    @GET("countries")
    fun getCountries(): Observable<HolidayResponse>
}