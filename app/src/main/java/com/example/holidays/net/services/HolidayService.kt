package com.example.holidays.net.services

import com.example.holidays.net.responses.HolidayResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HolidayService {

    @GET("holidays")
    fun getHolidays(
        @Query("country") country: String = "US",
        @Query("year") year: Int = 2019
    ): Observable<HolidayResponse>
}