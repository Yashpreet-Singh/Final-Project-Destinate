package com.example.finalprojectdestinate.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectdestinate.R
import com.example.finalprojectdestinate.model.Flight

class FlightAdapter(private val flights: List<Flight>) : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flight, parent, false)
        return FlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        holder.bind(flights[position])
        Log.d("FlightAdapter", "Binding flight at position $position: ${flights[position]}")
    }


    override fun getItemCount() = flights.size

    class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCompanyShortName: TextView = itemView.findViewById(R.id.tvCompanyShortName)
        private val tvDepartureName: TextView = itemView.findViewById(R.id.tvDepartureName)
        private val tvArrivalCode: TextView = itemView.findViewById(R.id.tvArrivalCode)
        private val tvDepartureDateTime: TextView = itemView.findViewById(R.id.tvDepartureDateTime)
        private val tvArrivalDateTime: TextView = itemView.findViewById(R.id.tvArrivalDateTime)

        fun bind(flight: Flight) {
            tvCompanyShortName.text = flight.companyShortName
            tvDepartureName.text = flight.departureName
            tvArrivalCode.text = flight.arrivalCode
            tvDepartureDateTime.text = flight.departureDateTime
            tvArrivalDateTime.text = flight.arrivalDateTime
            Log.d("Flight Adapter", "Flight: $tvCompanyShortName")

        }
    }
}
