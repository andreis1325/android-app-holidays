package com.example.holidays.net.services

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiRest {
    companion object {

        private var BASE_URL = "https://calendarific.com/api/v2/"
        private var KEY = "e56f075e3163fe92464fe6229d911a71404e8cef"

        internal fun getApi(): Retrofit {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC

            val client = getOkHttpClient()

            val builder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(getGsonConverterFactory())
                .client(client)

            return builder.build()
        }

        private fun getGsonConverterFactory(): Converter.Factory {
            return GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        }

        private fun getOkHttpClient(): OkHttpClient {
            return OkHttpClient()
                .newBuilder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                    val requestBuilder = chain.request().url
                    val url = requestBuilder.newBuilder()
                        .addQueryParameter("api_key", KEY)
                        .build()

                    request.url(url)
                    chain.proceed(request.build())
                }
                .addNetworkInterceptor(Interceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                    chain.proceed(requestBuilder.build())

                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
        }
    }
}
