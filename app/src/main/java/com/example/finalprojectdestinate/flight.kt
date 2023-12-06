package com.example.finalprojectdestinate.api

data class Flight(
    val id: Int,
    val airline: String,
    val destination: String,
    val departureTime: String,
    val price: Double
)
