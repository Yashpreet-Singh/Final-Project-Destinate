package com.example.finalprojectdestinate

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.InputDevice
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.findFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_guide, container, false)

        fusedLocationProviderClient  = LocationServices.getFusedLocationProviderClient(requireContext())
        getUserLocation()

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GuideFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuideFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        //myGoogleMap =googleMap
        val SYR = LatLng(currentLocation.latitude, currentLocation.longitude)
        Log.i("lockk",currentLocation.toString())

        Log.d("loc",(currentLocation.latitude).toString())
        googleMap.addMarker(
            MarkerOptions()
                .position(SYR)
                .title("Syracuse, NY")
        )
//        val markerOptions = MarkerOptions()
//        markerOptions.position(SYR)
//        markerOptions.title("Syracuse, NY")
//        markerOptions.snippet("Center of the Universe")
//        googleMap.addMarker(markerOptions)

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SYR))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SYR, 8f))
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(8f))



    }

    private fun getUserLocation(){

        if (requireActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && requireActivity().checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){


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


}