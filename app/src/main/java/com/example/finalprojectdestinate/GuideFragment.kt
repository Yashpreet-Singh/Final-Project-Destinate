package com.example.finalprojectdestinate

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import java.util.Locale


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuideFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //private var myGoogleMap: GoogleMap? = null
    private var myLat: Double = 0.0
    private var myLng: Double = 0.0
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var currentLocation :Location
    private lateinit var searchboxValue :String
    private lateinit var city :String
    private lateinit var  dialog : AlertDialog

    private val myDB: DatabaseHelper by lazy { DatabaseHelper(requireContext()) }





    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_guide, container, false)

        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(R.layout.progress)
        dialog = builder.create()
        dialog.show()




//        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        if (requireContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//            && requireContext().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//
//
//            //Access for it
//            requireActivity().requestPermissions(arrayOf( android.Manifest.permission.ACCESS_FINE_LOCATION),
//                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
//
//            //display a toast message
//            //Toast.makeText(requireContext(), "No Location Permission Granted", Toast.LENGTH_SHORT).show()
//
//
//        }
//
//        var loc: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//
//        if (loc == null) {
//            // Fall back to network if GPS is not available
//            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//        }
//
//        if (loc != null) {
//            myLat = loc.latitude
//            myLng = loc.longitude
//            // Your code here using myLat and myLng
//        }





//        val mainScrollView: NestedScrollView = view.findViewById(R.id.scrollView)
//        val transparentImageView: ImageView = view.findViewById(R.id.transparent_image)
//
//        transparentImageView.setOnGenericMotionListener { _, event ->
//            if (event.source and InputDevice.SOURCE_MOUSE != 0) {
//                when (event.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        // Disallow ScrollView to intercept touch events.
//                        mainScrollView.requestDisallowInterceptTouchEvent(true)
//                        // Disable touch on transparent view
//                        view.performClick()
//                        false
//                    }
//
//                    MotionEvent.ACTION_UP -> {
//                        // Allow ScrollView to intercept touch events.
//                        mainScrollView.requestDisallowInterceptTouchEvent(false)
//
//                        true
//                    }
//
//                    MotionEvent.ACTION_MOVE -> {
//                        mainScrollView.requestDisallowInterceptTouchEvent(true)
//                        false
//                    }
//
//                    else -> true
//                }
//            } else{
//                false
//            }
//        }
//


        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fusedLocationProviderClient  = LocationServices.getFusedLocationProviderClient(requireContext())


        getUserLocation() // get location on view created

        //get search results on view created
        //val activity: MainActivity? = activity as MainActivity?
        //searchboxValue = activity?.getSearchValue().toString()
        //Log.d("itemfrommain", searchboxValue)

        //Retrieve from datbase
       searchboxValue = myDB.getCurrentSearch().toString()



    }



    override fun onMapReady(googleMap: GoogleMap) {
        //myGoogleMap =googleMap

        val currentpos = LatLng(currentLocation.latitude, currentLocation.longitude)
        val searchLatLng = LatLng(getLatLong(searchboxValue)[0],getLatLong(searchboxValue)[1])


        Log.i("lockk",currentLocation.toString())
        Log.d("loc", getAddress(currentLocation))
        Log.d("loc", getLatLong("Syracuse").toString())

        //add at current location
        googleMap.addMarker(
            MarkerOptions()
                .position(currentpos)
                .title(getAddress(currentLocation))
        )


        //aad marker at the search value
        googleMap.addMarker(
            MarkerOptions()
                .position(searchLatLng)
                .title(searchboxValue)
        )
//
        googleMap.addPolyline(
            PolylineOptions()
                .add(currentpos)
                .add(searchLatLng)

        )

//        val markerOptions = MarkerOptions()
//        markerOptions.position(SYR)
//        markerOptions.title("Syracuse, NY")
//        markerOptions.snippet("Center of the Universe")
//        googleMap.addMarker(markerOptions)

        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(pos))
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 8f))
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(8f))
        val builder = LatLngBounds.Builder()
        builder.include(currentpos)
        builder.include(searchLatLng)
        val bounds = builder.build()
        val padding = 0 // padding around start and end marker
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.animateCamera(cu)
        googleMap.uiSettings.isZoomControlsEnabled= true
        googleMap.uiSettings.isCompassEnabled=true


        dialog.cancel()


    }

    private fun getUserLocation(){

        if (requireActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && requireActivity().checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){



            //Access for it
            requireActivity().requestPermissions(arrayOf( android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)

            //display a toast message
            //Toast.makeText(requireContext(), "No Location Permission Granted", Toast.LENGTH_SHORT).show()

            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location ->

                if (location != null)
                {
                    currentLocation = location
                    Log.i("lockk here ",currentLocation.toString())
                    val mapFragment : SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                    mapFragment?.getMapAsync(this)

                    //send currentlocation to main activity
                    Log.d("sent to main", getAddress(currentLocation))
                    val activity: MainActivity? = activity as MainActivity?
                    activity?.setCurrentLocation(getAddress(currentLocation))
                }
                else {
                    Log.d("loc", "Current location not available")
                    // Show message to user
                    Toast.makeText(requireContext(), "Location unavailable", Toast.LENGTH_SHORT).show()
                }

        }





    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==
                PackageManager.PERMISSION_GRANTED){
                getUserLocation()

            }
        }
    }

    private fun getAddress(location: Location) : String{
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val address: Address?

        val addresses: List<Address>? =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)

        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                address = addresses[0]
                //fulladdress = address.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex
                city = address.locality;
            }
        }
        return city
    }

    fun getLatLong(name: String): ArrayList<Double>{

        // Create a Geocoder instance
        val geocoder = context?.let { Geocoder(it) }

        // Get the list of addresses for the specified location name
        val addresses = geocoder?.getFromLocationName(name, 1)

        // If the address list is not empty, get the latitude and longitude
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                myLat = addresses[0].latitude
                myLng = addresses[0].longitude

            }
        }
        return arrayListOf(myLat,myLng)
    }






}