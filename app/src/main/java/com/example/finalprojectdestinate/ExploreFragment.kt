package com.example.finalprojectdestinate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var userAgentData :String ="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36"

    private lateinit var youtubeframe1 : WebView
    private lateinit var youtubeframe2 : WebView
    private lateinit var youtubeframe3 : WebView
    private lateinit var youtubeframe4 : WebView

    private lateinit var searchCountry : TextView
    private lateinit var content : TextView

    private lateinit var mysearchValue :String
    private lateinit var searchboxValue :String
    private  var currentLocation : String? = null

    lateinit var locationListdb: ArrayList<TripData>
    private val myDB: DatabaseHelper by lazy { DatabaseHelper(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_explore, container, false)


        //get cuurent user from mainactivity
//        val fragment: PlanFragment? = requireParentFragment() as PlanFragment?
//        val currentUser = fragment?.giveuserData()
//
//        Log.d("item ine xl=plore", currentUser.toString())

        val activity: MainActivity? = activity as MainActivity?
        searchboxValue = activity?.getSearchValue().toString()
        Log.d("itemfrommain", searchboxValue)

        mysearchValue = searchboxValue


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //initializong my db and getting the values from db
        //myDB.initializeTables()
        locationListdb = myDB.getAllLocationData()

        searchCountry =view.findViewById(R.id.search_country)
        content = view.findViewById(R.id.overview_content)
        //view.findViewById<TextView>(R.id.search_country)
        //view.findViewById<TextView>(R.id.search_country)

        if (!checkLocationInDB(view)){
            Toast.makeText(requireContext(), "Place not in database..setting to current location", Toast.LENGTH_LONG).show()
            //get current location from mainactivity where mainactivity gets it location from guide fragment

            val activity: MainActivity? = activity as MainActivity?
            currentLocation = activity?.getCurrentLocation() //will get name


            //Log.d("cuurent in explore", currentLocation!!)

            //set search value to current location only in Explore Fragemnt
            if(currentLocation != null) {
                mysearchValue = currentLocation as String
                checkLocationInDB(view)
            }
            else{
                Toast.makeText(requireContext(), "No current location...setting to Default", Toast.LENGTH_LONG).show()
                mysearchValue = "Syracuse"
                checkLocationInDB(view)
            }

        }


    }

    private fun checkLocationInDB(view:View):Boolean {

        for (place in locationListdb) {
            if (place.location.equals(mysearchValue, ignoreCase = true)) {


                //initialize data
                searchCountry.text = place.location
                content.text = place.overview

                youtubeframe1 = view.findViewById(R.id.youtube_vid1)
                val video1 = place.tourismVid!![0]
                youtubeframe1.loadData(video1, "text/html", "uft-8")
                youtubeframe1.settings.javaScriptEnabled = true
                youtubeframe1.webChromeClient = WebChromeClient()
                youtubeframe1.webViewClient = WebViewClient()
                youtubeframe1.settings.userAgentString = userAgentData

                youtubeframe2 = view.findViewById(R.id.youtube_vid2)
                val video2 = place.tourismVid[1]
                youtubeframe2.loadData(video2, "text/html", "uft-8")
                youtubeframe2.settings.javaScriptEnabled = true
                youtubeframe2.webChromeClient = WebChromeClient()
                youtubeframe2.webViewClient = WebViewClient()
                youtubeframe2.settings.userAgentString = userAgentData

                return true
            }
        }

        return false
    }


}