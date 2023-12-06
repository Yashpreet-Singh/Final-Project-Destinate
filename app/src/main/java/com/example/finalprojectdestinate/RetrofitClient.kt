package com.example.finalprojectdestinate.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.skyscanner.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
