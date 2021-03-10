package com.example.holidays

import android.app.Application
import android.content.SharedPreferences
import com.example.holidays.net.services.ApiRest
import com.example.holidays.model.PreferencesUtils
import com.example.holidays.net.repo.CountryRepo
import com.example.holidays.net.repo.HolidayRepo
import com.example.holidays.net.repo.SharedPreferencesRepo
import com.example.holidays.net.services.CountryService
import com.example.holidays.net.services.HolidayService
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.*
import retrofit2.Retrofit

private lateinit var kodeinStored: DI

class MyApp : Application() {


    private val settingModule = DI.Module("Settings module") {

        bind<SharedPreferences>() with singleton {
            PreferencesUtils.getSharedPreferences(applicationContext)
        }

        bind<SharedPreferencesRepo>() with singleton {
            SharedPreferencesRepo()
        }
        bind<Retrofit>() with singleton { ApiRest.getApi() }

        bind<CompositeDisposable>() with provider { CompositeDisposable() }
        bind<HolidayRepo>() with singleton {
            HolidayRepo(
                instance<Retrofit>().create(
                    HolidayService::class.java
                )
            )
        }
        bind<CountryRepo>() with singleton {
            CountryRepo(
                instance<Retrofit>().create(
                    CountryService::class.java
                )
            )
        }

    }


    companion object {
        var kodein: DI
            get() = kodeinStored
            set(_) {}
    }

    override fun onCreate() {
        super.onCreate()

        if (::kodeinStored.isInitialized.not())
            kodeinStored = DI {
                import(settingModule)
            }
        initCalligraphy()
    }

    private fun initCalligraphy() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("font/OpenSans-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }
}