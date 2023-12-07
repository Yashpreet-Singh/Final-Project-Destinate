package com.example.finalprojectdestinate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectdestinate.R
import com.example.finalprojectdestinate.FlightLegDetails  // Import FlightLegDetails

class FlightAdapter(private val flights: List<FlightLegDetails>) : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {

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
        holder.tvDepartureName.text = flightDetail.flsDepartureName
        holder.tvArrivalCode.text = flightDetail.flsArrivalCode
        holder.tvDepartureDateTime.text = flightDetail.flsDepartureDateTime
        holder.tvArrivalDateTime.text = flightDetail.flsArrivalDateTime
        // ... other data bindings as needed ...
    }

    override fun getItemCount() = flights.size
}
