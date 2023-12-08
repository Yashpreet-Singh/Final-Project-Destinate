package com.example.finalprojectdestinate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [CommunityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommunityFragment : Fragment(), RecycleAdapter.MyItemClickListener{
    // TODO: Rename and change types of parameters


    private lateinit var myAdapter: RecycleAdapter
    lateinit var locationListdb: ArrayList<TripData>
    lateinit var userListdb: ArrayList<UserData>
    private val myDB: DatabaseHelper by lazy { DatabaseHelper(requireContext()) }

    private lateinit var addButton: ImageButton
    private lateinit var addCard : CardView
    private lateinit var postButton: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var likeButton: ImageButton

    private lateinit var currentUser: String


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)



        //when you press backbutton while you are in communitiy fragment, goes back to plan fragment
        val onBackPressedCallback =object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_communityFragment_to_planFragment)
            }

        }
        //regesister the backpress to this fragment
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)


        //get cuurent user from mainactivity
        val activity: MainActivity? = activity as MainActivity?
        currentUser = activity?.getCurrentUser().toString()


        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView1)


        //initializong my db and getting the values from db
        //myDB.initializeTables()
        //locationListdb = myDB.getAllLocationData()
        userListdb=myDB.getAllUserData()

        Log.i("main data",userListdb.toString())



        // create vertical layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // giving my adapter the database to make changes
        myAdapter = RecycleAdapter(userListdb,requireContext(), myDB)

        myAdapter.setMyItemClickListener(this) //Register ItemClick listener

        recyclerView.adapter = myAdapter

        //initialize card
        addCard =view.findViewById(R.id.myadd_cardview)

        // Find the add button by its ID
        addButton = view.findViewById(R.id.add_button)
        // Set up a click listener
        addButton.setOnClickListener {

            //make add_cardview visible
            addCard.visibility = View.VISIBLE


        }

        // Find the add button by its ID
        postButton = view.findViewById(R.id.postButton)
        // Set up a click listener
        postButton.setOnClickListener {

            //make add_cardview gone
            addCard.visibility = View.GONE

        }


    }
    companion object {


    }

    override fun onItemClickedFromAdapter(user: UserData) {
        TODO("Not yet implemented")
    }


    override fun onStart() {
        super.onStart()


    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        myDB.closeDB()

    }




}