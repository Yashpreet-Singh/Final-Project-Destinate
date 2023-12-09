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
import android.widget.Button
import android.widget.EditText
import kotlin.math.log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Request
import java.io.IOException
import org.json.JSONObject
import org.simpleframework.xml.core.Persister
import org.json.JSONArray
import com.example.finalprojectdestinate.model.Flight
import com.example.finalprojectdestinate.FlightDetails
import com.example.finalprojectdestinate.OTA_AirDetailsRS
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import android.app.DatePickerDialog
import java.util.Calendar

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
        etDate.setOnClickListener {
            showDatePicker()
        }
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

        rvFlights = view.findViewById<RecyclerView>(R.id.rvFlights)
        rvFlights.layoutManager = LinearLayoutManager(context)
        rvFlights.adapter = FlightAdapter(listOfFlights)
    }
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            view?.findViewById<EditText>(R.id.etDate)?.setText(formattedDate)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        datePickerDialog.datePicker.calendarViewShown = true
        datePickerDialog.datePicker.spinnersShown = false
        datePickerDialog.show()
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
        val json = readIataCodesJson() // Assuming this function correctly fetches your JSON string
        val jsonObject = JSONObject(json)
        val trimmedCityName = cityName.trim()

        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            if (jsonObject.getString(key).equals(trimmedCityName, ignoreCase = true)) {
                return key // Return the key (IATA code) if the city name matches
            }
        }

        Log.d("BookFragment", "City name key not found: $trimmedCityName")
        return ""
    }

    private fun JSONObject.toMap(): Map<String, String> = keys().asSequence().associateWith {
        getString(it)
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
                val url = "https://timetable-lookup.p.rapidapi.com/TimeTable/$fromIataCode/$toIataCode/$formattedDate/"
                Log.d("BookFragment", "the URL: $url")
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
                    Log.d("BookFragment", "FirstcallFlightresponse: $flights")

                    CoroutineScope(Dispatchers.Main).launch {
                        listOfFlights.clear()
                        listOfFlights.addAll(flights)
                        rvFlights.adapter?.notifyDataSetChanged()
                    }
                } else {
                    Log.e("BookFragment", "Error fetching flight details")
                }
            } catch (e: Exception) {
                Log.e("BookFragment", "Error fetching flight details: ${e.message}", e)
            }
        }
    }
    private fun parseFlightResponse(xmlString: String): MutableList<Flight> {
        val flightList = mutableListOf<Flight>()

        try {
            val xmlPullParserFactory = XmlPullParserFactory.newInstance()
            val xmlPullParser = xmlPullParserFactory.newPullParser()
            xmlPullParser.setInput(StringReader(xmlString))

            var eventType = xmlPullParser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && xmlPullParser.name == "FlightLegDetails") {
                    val flight = parseFlightLegDetails(xmlPullParser)
                    Log.d("BookFragment", "ParseFlightResponsefunction: $flight")
                    flightList.add(flight)
                    Log.d("BookFragment", "Added flight: $flight")
                }
                eventType = xmlPullParser.next()
            }
        } catch (e: Exception) {
            Log.e("BookFragment", "Error parsing flight details: ${e.message}", e)
        }

        return flightList
    }

    private fun parseFlightLegDetails(xmlPullParser: XmlPullParser): Flight {
        var companyShortName: String? = null
        var departureName: String? = null
        var arrivalCode: String? = null
        var departureDateTime: String? = null
        var arrivalDateTime: String? = null

        while (!(xmlPullParser.eventType == XmlPullParser.END_TAG && xmlPullParser.name == "FlightLegDetails")) {
            if (xmlPullParser.eventType == XmlPullParser.START_TAG) {
                Log.d("BookFragment", "Parsername:$xmlPullParser.name.toString()")

                when (xmlPullParser.name) {

                    "MarketingAirline" -> {
                        companyShortName = xmlPullParser.getAttributeValue(null, "CompanyShortName")
                        Log.d("BookFragment", "MarketingAirline: $companyShortName")
                    }
                    "DepartureAirport" -> {
                        departureName = xmlPullParser.getAttributeValue(null, "FLSLocationName")
                        Log.d("BookFragment", "DepartureAirport: $departureName")
                    }
                    "ArrivalAirport" -> {
                        arrivalCode = xmlPullParser.getAttributeValue(null, "LocationCode")
                        Log.d("BookFragment", "ArrivalAirport: $arrivalCode")
                    }
                    "FlightLegDetails" -> {
                        departureDateTime = xmlPullParser.getAttributeValue(null, "DepartureDateTime")
                        arrivalDateTime = xmlPullParser.getAttributeValue(null, "ArrivalDateTime")
                        Log.d("BookFragment", "DepartureDateTime: $departureDateTime")
                        Log.d("BookFragment", "ArrivalDateTime: $arrivalDateTime")
                    }
                }
            }
            xmlPullParser.next()
        }

        return Flight(
            companyShortName = companyShortName ?: "N/A",
            departureName = departureName ?: "N/A",
            arrivalCode = arrivalCode ?: "N/A",
            departureDateTime = departureDateTime ?: "N/A",
            arrivalDateTime = arrivalDateTime ?: "N/A"
        )
    }

}