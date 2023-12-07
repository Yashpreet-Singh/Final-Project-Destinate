package com.example.finalprojectdestinate.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitClient {
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://timetable-lookup.p.rapidapi.com/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }
}

val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

// Add this client to your Retrofit builder
