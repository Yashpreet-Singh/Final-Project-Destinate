package com.example.finalprojectdestinate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.*

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
        fetchFlights()
    }

    private fun fetchFlights() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val flightList = flightApiService.getFlights("YOUR_API_KEY")
                withContext(Dispatchers.Main) {
                    // Update UI with flight data
                    // e.g., use a RecyclerView adapter to display flights
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}