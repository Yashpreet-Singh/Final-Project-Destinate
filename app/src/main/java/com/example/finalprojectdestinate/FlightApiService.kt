package com.example.finalprojectdestinate

import com.example.finalprojectdestinate.FlightDetails
import retrofit2.http.GET
import retrofit2.http.Headers

interface FlightApiService {
    @Headers("X-RapidAPI-Key: a40635331cmsh7c3b66b7339aa65p18a568jsnce6433174bfa", "X-RapidAPI-Host: timetable-lookup.p.rapidapi.com")
    @GET("TimeTable/BOS/LAX/20231217/")
    suspend fun getFlightDetails(): FlightDetails
}
