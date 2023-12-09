package com.example.finalprojectdestinate

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "OTA_AirDetailsRS", strict = false)
data class OTA_AirDetailsRS @JvmOverloads constructor(
    @field:ElementList(entry = "FlightDetails", inline = true, required = false)
    var flightDetailsList: List<FlightDetails>? = null
)

@Root(name = "FlightDetails", strict = false)
data class FlightDetails @JvmOverloads constructor(
    @field:ElementList(entry = "FlightLegDetails", inline = true, required = false)
    var flightLegDetails: List<FlightLegDetails>? = null
)

@Root(name = "FlightLegDetails", strict = false)
data class FlightLegDetails @JvmOverloads constructor(
    @field:Element(name = "DepartureDateTime", required = false)
    var departureDateTime: String? = null,

    @field:Element(name = "ArrivalDateTime", required = false)
    var arrivalDateTime: String? = null,

    @field:Element(name = "FlightNumber", required = false)
    var flightNumber: String? = null,

    @field:Element(name = "DepartureAirport", required = false)
    var departureAirport: Airport? = null,

    @field:Element(name = "ArrivalAirport", required = false)
    var arrivalAirport: Airport? = null,

    @field:Element(name = "MarketingAirline", required = false)
    var marketingAirline: Airline? = null
)

@Root(name = "DepartureAirport", strict = false)
data class Airport @JvmOverloads constructor(
    @field:Element(name = "LocationCode", required = false)
    var locationCode: String? = null,

    @field:Element(name = "FLSLocationName", required = false)
    var locationName: String? = null,

    // Add additional elements as required from the XML
)

@Root(name = "MarketingAirline", strict = false)
data class Airline @JvmOverloads constructor(
    @field:Element(name = "CompanyShortName", required = false)
    var shortName: String? = null

    // Add additional elements as required from the XML
)
