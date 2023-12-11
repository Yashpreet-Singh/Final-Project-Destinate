package com.example.finalprojectdestinate

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [CommunityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommunityFragment : Fragment(), RecycleAdapter.MyItemClickListener{
    // TODO: Rename and change types of parameters

    private val CHANNEL_ID = "MyChannelID"
    private val NOTIFICATION_ID = 1
    private lateinit var myAdapter: RecycleAdapter
    lateinit var locationListdb: ArrayList<TripData>
    lateinit var userListdb: ArrayList<UserData>
    private val myDB: DatabaseHelper by lazy { DatabaseHelper(requireContext()) }

    private lateinit var addButton: ImageButton
    private lateinit var addCard : CardView
    private lateinit var postButton: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var likeButton: ImageButton

    private var currentUser : String? =null

    private lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder


    private lateinit var addtitle: EditText
    private lateinit var addimageurl:EditText
    private lateinit var currentUserFullName: TextView

    private var currentUserisLiked by Delegates.notNull<Boolean>()

    private lateinit var myNewUser :UserData


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
//        val onBackPressedCallback =object: OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController().navigate(R.id.action_communityFragment_to_planFragment)
//            }
//
//        }
//        //regesister the backpress to this fragment
//        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)


        //get cuurent user from mainactivity
//        val activity: MainActivity? = activity as MainActivity?
//        currentUser = activity?.getCurrentUser().toString()

        currentUser=myDB.getCurrentUser()
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView1)

        // Initialize NotificationManager

        notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //initializong my db and getting the values from db
        //myDB.initializeTables()
        //locationListdb = myDB.getAllLocationData()
        userListdb=myDB.getAllUserData()

        Log.i("main data",userListdb.toString())



        // create vertical layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // giving my adapter the database to make changes
        myAdapter = RecycleAdapter(userListdb,requireContext(), myDB,currentUser)

        myAdapter.setMyItemClickListener(this) //Register ItemClick listener

        recyclerView.adapter = myAdapter

        //animation for adapter
        val scaleAdapter = ScaleInAnimationAdapter(myAdapter).apply {
            setDuration(500)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
        recyclerView.adapter = scaleAdapter


        // Apply item animator from Wasabeef library
        recyclerView.itemAnimator = SlideInUpAnimator(OvershootInterpolator()).apply {
            addDuration = 500
            removeDuration = 500
        }


        //initialize card
        addCard =view.findViewById(R.id.myadd_cardview)

        addtitle =view.findViewById(R.id.add_title)
        addimageurl =view.findViewById(R.id.add_imageurl)

        //set user name curretusername

        currentUserFullName= view.findViewById(R.id.current_user)






        // Find the add button by its ID
        addButton = view.findViewById(R.id.add_button)
        // Set up a click listener
        addButton.setOnClickListener {

            //make add_cardview visible
            addCard.visibility = View.VISIBLE

            //find details of current user and copy it , only update if user hit post button
            for (user in userListdb){
                if(user.username == currentUser){

                    myNewUser = user.copy()
                    currentUserFullName.text= user.firstname + " " + user.lastname

//                //getting from  cuurent user
//                myNewUser.username = currentUser
//                myNewUser.lastname = user.lastname.toString()
//                myNewUser.password = user.password.toString()
//                myNewUser.firstname = user.firstname.toString()
//                myNewUser.isLiked = user.isLiked == tru

                }
            }


        }

        // Find the add button by its ID
        postButton = view.findViewById(R.id.postButton)
        // Set up a click listener
        postButton.setOnClickListener {



            //added later
            myNewUser.title = addtitle.text.toString().trim()
            myNewUser.myposts =addimageurl.text.toString().trim()
            myNewUser.isLiked = false //giving false to the button



            if (myNewUser.title!!.isNotEmpty() && myNewUser.myposts!!.isNotEmpty()) {

                //update the recycel list
                myAdapter.addUser(myNewUser)
                //userListdb.add(myNewUser) // list from community fragment
                //recyclerView.getChildAt( userListdb.count { it.myposts!!.isNotEmpty() } +1 )

                //add the new post and title for the user
                myDB.addUsers(myNewUser)

                //make feild empty
                addtitle.text.clear()
                addimageurl.text.clear()

                Toast.makeText(context, "Post uploaded!", Toast.LENGTH_SHORT).show()

                displayNotification()

                //make add_cardview gone
                //addCard.visibility = View.GONE

            } else {

                Toast.makeText(context, "title and imageurl cannot be empty,database not updated", Toast.LENGTH_SHORT).show()
                //make add_cardview gone
                //addCard.visibility = View.GONE
            }

            //add the new post and titile for the user
            //myDB.addUsers(myNewUser)

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

    private fun displayNotification() {

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "My Channel Description"
        notificationManager.createNotificationChannel(channel)

        // Build the notification
        builder = Notification.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle("Post Uploaded")
            .setContentText("Check out the latest post!")
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setAutoCancel(true)
            .setPriority(Notification.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)


        // Notify
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }







}