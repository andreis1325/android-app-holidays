package com.example.holidays.net.responses

import com.google.gson.annotations.SerializedName

data class HolidayResponse (
    val meta: Meta? = null,
    val response: Response? =null
)

data class Meta (
    val code: Long? = null
)

data class Response (
    val holidays: ArrayList<Holiday> = arrayListOf(),
    val countries: ArrayList<Country> = arrayListOf()
)

data class Country(
    @SerializedName("country_name")     val countryName: String? = null,
    @SerializedName("iso-3166")         val iso: String? = null
)

data class Holiday (
    val name: String? = null,
    val description: String? = null,
    val date: Date? = null,
    val type: List<String>? = null
)

data class Date (
    val iso: String? = null,
    val datetime: Datetime? = null
)

data class Datetime (
    val year: Long? = null,
    val month: Long? = null,
    val day: Long? = null
)
