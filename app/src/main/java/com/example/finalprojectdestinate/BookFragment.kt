package com.example.finalprojectdestinate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectdestinate.adapters.FlightAdapter
import android.util.Log
import kotlin.math.log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Request
import android.widget.EditText
import android.widget.Button
import java.io.IOException
import org.json.JSONObject
import org.json.JSONArray
import com.example.finalprojectdestinate.model.Flight

class BookFragment : Fragment() {
    private lateinit var rvFlights: RecyclerView
    private var listOfFlights = mutableListOf<Flight>()
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
        val listOfFlights = mutableListOf<Flight>()

        rvFlights = view.findViewById<RecyclerView>(R.id.rvFlights)
        rvFlights.layoutManager = LinearLayoutManager(context)
        rvFlights.adapter = FlightAdapter(listOfFlights)
    }
    private fun readIataCodesJson(): String {
        return try {
            val inputStream = requireContext().assets.open("IATA_Codes_CityNames.json")
            inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            Log.e("BookFragment", "Error reading IATA codes: ${e.message}", e)
            ""
        }
    }
    private fun getIataCode(cityName: String): String {
        val json = readIataCodesJson()
        val jsonObject = JSONObject(json)
        val trimmedCityName = cityName.trim()

        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            if (key.equals(trimmedCityName, ignoreCase = true)) {
                return jsonObject.getString(key)
            }
        }

        Log.d("BookFragment", "City name key not found: $trimmedCityName")
        return ""
    }



    private fun fetchFlights(fromLocation: String, toLocation: String, date: String) {
        val fromIataCode = getIataCode(fromLocation)
        val toIataCode = getIataCode(toLocation)

        Log.d("BookFragment", "From City: $fromLocation, IATA: $fromIataCode")
        Log.d("BookFragment", "To City: $toLocation, IATA: $toIataCode")

        if (fromIataCode.isBlank() || toIataCode.isBlank()) {
            Log.e("BookFragment", "IATA code not found for one or both cities")
            return
        }
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
                    val responseBody = response.body?.string() ?: ""
                    Log.d("BookFragment", "Flight details: $responseBody")
                    val flights = parseFlightResponse(responseBody)

                    CoroutineScope(Dispatchers.Main).launch {
                        listOfFlights.clear()
                        listOfFlights.addAll(flights)
                        rvFlights.adapter?.notifyDataSetChanged()                     }
                } else {
                    Log.e("BookFragment", "Error fetching flight details")
                }
            } catch (e: Exception) {
                Log.e("BookFragment", "Error fetching flight details: ${e.message}", e)
            }
        }
    }
    private fun parseFlightResponse(jsonString: String): MutableList<Flight> {
        val flightList = mutableListOf<Flight>()
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val flight = Flight(
                companyShortName = jsonObject.getString("companyShortName"),
                departureName = jsonObject.getString("departureName"),
                arrivalCode = jsonObject.getString("arrivalCode"),
                departureDateTime = jsonObject.getString("departureDateTime"),
                arrivalDateTime = jsonObject.getString("arrivalDateTime")
            )
            flightList.add(flight)
        }
        return flightList
    }

    companion object {

     }
}