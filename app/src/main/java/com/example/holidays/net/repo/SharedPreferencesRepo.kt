package com.example.holidays.net.repo

import android.content.SharedPreferences
import com.example.holidays.MyApp
import com.example.holidays.model.country
import com.example.holidays.model.iso
import com.example.holidays.model.year
import org.kodein.di.instance

class SharedPreferencesRepo {
    private val sharedPreferences by MyApp.kodein.instance<SharedPreferences>()

    fun getCountry(): String{
        return sharedPreferences.country ?: ""
    }

    fun getIso(): String{
        return sharedPreferences.iso ?: ""
    }

    fun getYear(): Int{
        return sharedPreferences.year
    }

    fun setCountry(country: String?){
        sharedPreferences.country = country
    }

    fun setIso(iso: String?){
        sharedPreferences.iso = iso
    }

    fun setYear(year: Int){
        sharedPreferences.year = year
    }
}
