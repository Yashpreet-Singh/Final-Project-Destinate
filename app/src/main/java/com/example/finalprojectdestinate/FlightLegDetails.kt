package com.example.finalprojectdestinate

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "FlightDetails", strict = false)
data class FlightDetails @JvmOverloads constructor(
    @field:Element(name = "FlightLegDetails", required = false)
    var flightLegDetails: List<FlightLegDetails>? = null
)
@Root(name = "FlightLegDetails", strict = false)
data class FlightLegDetails @JvmOverloads constructor(
    @field:Element(name = "DepartureAirport", required = false)
    var departureAirport: DepartureAirport? = null,

    @field:Element(name = "ArrivalAirport", required = false)
    var arrivalAirport: ArrivalAirport? = null,

    @field:Element(name = "MarketingAirline", required = false)
    var marketingAirline: MarketingAirline? = null,

    @field:Element(name = "FlightNumber", required = false)
    var flightNumber: String? = null

)

@Root(name = "DepartureAirport", strict = false)
data class DepartureAirport @JvmOverloads constructor(
    @field:Element(name = "FLSLocationName", required = false)
    var locationName: String? = null
    // Include additional properties as required
)

@Root(name = "ArrivalAirport", strict = false)
data class ArrivalAirport @JvmOverloads constructor(
    @field:Element(name = "FLSLocationName", required = false)
    var locationName: String? = null
    // Include additional properties as required
)

@Root(name = "MarketingAirline", strict = false)
data class MarketingAirline @JvmOverloads constructor(
    @field:Element(name = "CompanyShortName", required = false)
    var companyShortName: String? = null
    // Include additional properties as required
)
