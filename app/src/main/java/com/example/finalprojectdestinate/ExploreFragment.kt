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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.mikhaellopez.circularimageview.CircularImageView
import java.util.Locale

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

    private lateinit var actImage1 : CircularImageView
    private lateinit var actImage2 : CircularImageView
    private lateinit var actImage3 : CircularImageView

    private lateinit var actDetailText1 : TextView
    private lateinit var actDetailText2 : TextView
    private lateinit var actDetailText3 : TextView

    private lateinit var searchCountry : TextView
    private lateinit var content : TextView

    private lateinit var mysearchValue :String
    //private lateinit var searchboxValue :String
    private  var currentLocation : String? = null

    lateinit var locationListdb: ArrayList<TripData>
    private val myDB: DatabaseHelper by lazy { DatabaseHelper(requireContext()) }

    var ImageTable: MutableMap<String, Int> = mutableMapOf()



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

//        val activity: MainActivity? = activity as MainActivity?
//        searchboxValue = activity?.getSearchValue().toString()

        //retrieve from database

        mysearchValue= myDB.getCurrentSearch()
        Log.i("From explore",mysearchValue)

        //mysearchValue = searchboxValue


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //initializong my db and getting the values from db
        //myDB.initializeTables()
        locationListdb = myDB.getAllLocationData()

        searchCountry =view.findViewById(R.id.search_country)
        content = view.findViewById(R.id.overview_content)

        actDetailText1 = view.findViewById(R.id.act1_detail)
        actDetailText2 = view.findViewById(R.id.act2_detail)
        actDetailText3 = view.findViewById(R.id.act3_detail)

        actImage1 =view.findViewById(R.id.act1)
        actImage2 =view.findViewById(R.id.act2)
        actImage3 =view.findViewById(R.id.act3)

        //view.findViewById<TextView>(R.id.search_country)
        //view.findViewById<TextView>(R.id.search_country)

        //setup images
        ImageTable["Hiking"]= R.drawable.hiking
        ImageTable["Sightseeing"]=R.drawable.sightseeing
        ImageTable["Bird Watching"]= R.drawable.birdwatching
        ImageTable["Kayaking"]=R.drawable.kayaking
        ImageTable["Fishing"]= R.drawable.fishing
        ImageTable["Cycling"]= R.drawable.cycling
        ImageTable["Zip Lining"]=R.drawable.ziplining
        ImageTable["Horseback Riding"]= R.drawable.horseriding
        ImageTable["Geocaching"]= R.drawable.geocoaching
//        ImageTable["Hiking"]=cx
//        ImageTable["Hiking"]=cx
//        ImageTable["Hiking"]=cx
//
//        ImageTable["Hiking"]=cx
//        ImageTable["Hiking"]=cx
//        ImageTable["Hiking"]=cx








        if (!checkLocationInDB(view)){
            Toast.makeText(requireContext(), "Place not in database..setting to current location", Toast.LENGTH_LONG).show()
            //get current location from mainactivity where mainactivity gets it location from guide fragment

            val activity: MainActivity? = activity as MainActivity?
            currentLocation = activity?.getCurrentLocation() //will get name


            //Log.d("cuurent in explore", currentLocation!!)

            //set search value to current location only in Explore Fragemnt
            if(currentLocation != null) {
                mysearchValue = currentLocation as String
                //update dtaa
                myDB.updateCurrentSearch(mysearchValue,0)

                checkLocationInDB(view)
            }
            else{
                Toast.makeText(requireContext(), "No current location...setting to Default", Toast.LENGTH_LONG).show()
                mysearchValue = "Syracuse"
                //update data
                myDB.updateCurrentSearch(mysearchValue,0)


                checkLocationInDB(view)
            }

        }


    }

    private fun checkLocationInDB(view:View):Boolean {

        for (place in locationListdb) {
            if (place.location!!.toLowerCase(Locale.ROOT) == mysearchValue.toLowerCase(Locale.ROOT)
            ) {


                //initialize data
                searchCountry.text = place.location
                content.text = place.overview

                actDetailText1.text =place.activities!![0]
                actDetailText2.text = place.activities[1]
                actDetailText3.text = place.activities[2]

                //giving image to map on refresh if happens

                actImage1.setImageResource(ImageTable[place.activities[0]]!!)
                actImage2.setImageResource(ImageTable[place.activities[1]]!!)
                actImage3.setImageResource(ImageTable[place.activities[2]]!!)



                youtubeframe1 = view.findViewById(R.id.youtube_vid1)
                configureWebView(youtubeframe1, place.tourismVid!![0])

                youtubeframe2 = view.findViewById(R.id.youtube_vid2)
                configureWebView(youtubeframe2, place.tourismVid[1])

                youtubeframe3 = view.findViewById(R.id.youtube_vid3)
                configureWebView(youtubeframe3, place.tourismVid[2])

                youtubeframe4 = view.findViewById(R.id.youtube_vid4)
                configureWebView(youtubeframe4, place.tourismVid[3])



//                youtubeframe1 = view.findViewById(R.id.youtube_vid1)
//                val video1 = place.tourismVid!![0]
//                youtubeframe1.loadData(video1, "text/html", "uft-8")
//                youtubeframe1.settings.javaScriptEnabled = true
//                youtubeframe1.webChromeClient = WebChromeClient()
//                youtubeframe1.webViewClient = WebViewClient()
//                youtubeframe1.settings.userAgentString = userAgentData
//
//                youtubeframe2 = view.findViewById(R.id.youtube_vid2)
//                val video2 = place.tourismVid[1]
//                youtubeframe2.loadData(video2, "text/html", "uft-8")
//                youtubeframe2.settings.javaScriptEnabled = true
//                youtubeframe2.webChromeClient = WebChromeClient()
//                youtubeframe2.webViewClient = WebViewClient()
//                youtubeframe2.settings.userAgentString = userAgentData

                return true
            }
        }

        return false
    }

    private fun configureWebView(webView: WebView, videoUrl: String) {
        webView.loadData(videoUrl, "text/html", "utf-8")
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        webView.settings.userAgentString = userAgentData
    }





}