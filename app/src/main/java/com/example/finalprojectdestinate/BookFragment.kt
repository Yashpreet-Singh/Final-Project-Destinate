package com.example.finalprojectdestinate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalprojectdestinate.api.FlightApiService
import com.example.finalprojectdestinate.api.RetrofitClient
import com.example.finalprojectdestinate.adapters.FlightAdapter

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
        val rvFlights = view.findViewById<RecyclerView>(R.id.rvFlights)
        rvFlights.layoutManager = LinearLayoutManager(context)
        rvFlights.adapter = FlightAdapter(emptyList())

        fetchFlights()
    }

    private fun fetchFlights() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val flightList = flightApiService.getFlights("YOUR_API_KEY")
                withContext(Dispatchers.Main) {
                    val rvFlights = view?.findViewById<RecyclerView>(R.id.rvFlights)
                    if (rvFlights != null) {
                        rvFlights.adapter = FlightAdapter(flightList)
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    companion object {

     }
}