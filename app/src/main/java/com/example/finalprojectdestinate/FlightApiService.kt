package com.example.finalprojectdestinate.api

import retrofit2.http.GET
import retrofit2.http.Query

interface FlightApiService {
    @GET("flights")
    suspend fun getFlights(@Query("apiKey") apiKey: String): List<Flight>
}
