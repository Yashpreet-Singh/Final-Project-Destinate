package com.example.finalprojectdestinate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectdestinate.R
import com.example.finalprojectdestinate.api.Flight

class FlightAdapter(private val flights: List<Flight>) :
    RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

    class FlightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAirline: TextView = view.findViewById(R.id.tvAirline)
        val tvDestination: TextView = view.findViewById(R.id.tvDestination)
        val tvDepartureTime: TextView = view.findViewById(R.id.tvDepartureTime)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flight, parent, false)
        return FlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = flights[position]
        holder.tvAirline.text = flight.airline
        holder.tvDestination.text = flight.destination
        holder.tvDepartureTime.text = flight.departureTime
        holder.tvPrice.text = "Price: ${flight.price}"
    }

    override fun getItemCount() = flights.size
}
