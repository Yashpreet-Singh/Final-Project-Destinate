package com.example.finalprojectdestinate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectdestinate.R
import com.example.finalprojectdestinate.model.Flight

class FlightAdapter(private var flights: MutableList<Flight>) : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

    class FlightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCompanyShortName: TextView = view.findViewById(R.id.tvCompanyShortName)
        val tvDepartureName: TextView = view.findViewById(R.id.tvDepartureName)
        val tvArrivalCode: TextView = view.findViewById(R.id.tvArrivalCode)
        val tvDepartureDateTime: TextView = view.findViewById(R.id.tvDepartureDateTime)
        val tvArrivalDateTime: TextView = view.findViewById(R.id.tvArrivalDateTime)
        // ... other views as needed ...
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flight, parent, false)
        return FlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flightDetail = flights[position]

        holder.tvCompanyShortName.text = flightDetail.companyShortName
        holder.tvDepartureName.text = flightDetail.departureName
        holder.tvArrivalCode.text = flightDetail.arrivalCode
        holder.tvDepartureDateTime.text = flightDetail.departureDateTime
        holder.tvArrivalDateTime.text = flightDetail.arrivalDateTime
    }

    override fun getItemCount() = flights.size

    fun updateFlights(newFlights: List<Flight>) {
        flights.clear()
        flights.addAll(newFlights)
        notifyDataSetChanged()
    }
}
