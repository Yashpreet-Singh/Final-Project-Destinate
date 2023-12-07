package com.example.finalprojectdestinate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectdestinate.FlightApiService
import com.example.finalprojectdestinate.api.RetrofitClient
import com.example.finalprojectdestinate.adapters.FlightAdapter
import android.util.Log
import kotlin.math.log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Request

class BookFragment : Fragment() {
    private val flightApiService by lazy {
        RetrofitClient.retrofitInstance.create(FlightApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFromLocation = view.findViewById<EditText>(R.id.etFromLocation)
        val etToLocation = view.findViewById<EditText>(R.id.etToLocation)
        val etDate = view.findViewById<EditText>(R.id.etDate)
        val btnSearchFlights = view.findViewById<Button>(R.id.btnSearchFlights)

        btnSearchFlights.setOnClickListener {
            val fromLocation = etFromLocation.text.toString()
            val toLocation = etToLocation.text.toString()
            val date = etDate.text.toString()

            if (fromLocation.isNotBlank() && toLocation.isNotBlank() && date.isNotBlank()) {
                fetchFlights(fromLocation, toLocation, date)
            } else {
                // Show error or alert to fill all fields
            }
        }
        val rvFlights = view.findViewById<RecyclerView>(R.id.rvFlights)
        rvFlights.layoutManager = LinearLayoutManager(context)
        rvFlights.adapter = FlightAdapter(emptyList())
    }
    private fun fetchFlights(fromLocation: String, toLocation: String, date: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val formattedDate = date.replace("-", "")
                val url = "https://timetable-lookup.p.rapidapi.com/TimeTable/$fromLocation/$toLocation/$formattedDate/"
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("X-RapidAPI-Key", "a40635331cmsh7c3b66b7339aa65p18a568jsnce6433174bfa")
                    .addHeader("X-RapidAPI-Host", "timetable-lookup.p.rapidapi.com")
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    Log.d("BookFragment", "Flight details: $responseBody")
                } else {
                    Log.e("BookFragment", "Error fetching flight details")
                }
            } catch (e: Exception) {
                Log.e("BookFragment", "Error fetching flight details: ${e.message}", e)
            }
        }
    }

    companion object {

     }
}