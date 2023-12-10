package com.example.finalprojectdestinate

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mikhaellopez.circularimageview.CircularImageView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerlayout: DrawerLayout
    private lateinit var  navController : NavController

    private var currentUser : String? =null
    private var currentUserFromSaved : String? =null
    private var currentUserFirstname : String? = null
    private  var currentUserLastName : String? = null
    lateinit var userListCreatedb: ArrayList<UserData>
    private val myDB: DatabaseHelper by lazy { DatabaseHelper(this) }

    private var currentUserImage : ByteArray? = null

    private var searchvalue : String = "Syracuse" //default value

    private var data: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //println("From login: $currentUserFromSaved")
       // println("From saved: $currentUser")


        //currentUserFromSaved= savedInstanceState?.getString("current user")
        // Fetching the stored data from the SharedPreference
//        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
//        val retrieved_data = sh.getString("current user info", "")


        // Setting the fetched data in the EditTexts
        //currentUserFromSaved = retrieved_data

        //Log.i("From login1",currentUser.toString())
        //Log.i("From saved1",currentUserFromSaved.toString())

        myDB.initializeTables()
        currentUser=myDB.getCurrentUser()
        userListCreatedb=myDB.getAllUserData()

//        if (currentUserFromSaved.isNullOrEmpty()){ //valued from savedsaved
//            println("first loop1")
//
//
//            //getting data from intent login, since there is no saved data
//            val currentUser1= intent.getStringExtra("Current_User_Name") //email
//            Log.i("From login3",currentUser1.toString())

        Log.i("From login1",currentUser.toString())

        if (currentUser.isNullOrBlank() ) { //value from login
            //back to login activity
            Log.i("From login2",currentUser.toString())

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
//        else{
//            currentUser = currentUser.toString()
//            Log.i("From login4",currentUser.toString())
//        }






        //val currentUser1= intent.getStringExtra("Current_User_Name")

        //myDB.initializeTables()
        //getting user list





        //supportActionBar?.setDisplayHomeAsUpEnabled(true)//activating toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //supportActionBar?.setLogo(R.drawable.destinate1)
        supportActionBar?.title="Destinate"




        drawerlayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.open_nav, R.string.close_nav)

        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val headerview: View = navigationView.getHeaderView(0)
        val defaultuser: TextView = headerview.findViewById(R.id.navi_name)
        val defaultemail: TextView = headerview.findViewById(R.id.navi_email)
        val userprofile : CircularImageView =headerview.findViewById(R.id.navi_user_image)

        for ( user in userListCreatedb){
            if(user.username == currentUser){
                currentUserFirstname = user.firstname.toString()
                currentUserLastName = user.lastname.toString()
                currentUserImage = user.profileImg
            }
        }

        defaultuser.text = currentUserFirstname + " "+ currentUserLastName
        defaultemail.text= currentUser

        val byteArray = currentUserImage

        if (byteArray!= null) {

            val bitmap: Bitmap? = BitmapFactory.decodeByteArray(
                byteArray, 0,
                byteArray?.size!!
            )

            if (bitmap != null) {
                userprofile.setImageBitmap(bitmap)
            } else {
                if (currentUserFirstname == "Yash") {
                    userprofile.setImageResource(R.drawable.yash)
                }
                if (currentUserFirstname == "Tabish") {
                    userprofile.setImageResource(R.drawable.tabish)
                }

            }
        }




        //when you press backbutton while drawer is open
        val onBackPressedCallback =object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                Log.d("back2","bCK DONE")
                if(drawerlayout.isDrawerOpen(GravityCompat.START)){
                    drawerlayout.closeDrawer(GravityCompat.START)  }
                else{
                    finish()
                }
            }

        }
        //regesister the backpress to this activity
        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)



        //setup bottom navigation
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        //setup navigation between fragments using nagvigation.xml (Navigation Component)
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        navController = navHostFragment.navController
//        setupWithNavController(bottomnav, navController) // menuitem in bottom_menu and their respective fragment must have same id

//        val bundle =Bundle()
//        bundle.putString("Search item","pan")
//        // val args= bundleOf("amount" to searchvalue)
//        //navController.setGraph(R.navigation.navigation,bundle)
//
//        val planfrag =PlanFragment()
//        planfrag.arguments =bundle




        bottomnav.selectedItemId = R.id.planFragment

        //deafult fargment display
        val plan = PlanFragment()
        supportFragmentManager.beginTransaction().replace(R.id.meContainer, plan).addToBackStack("null").commit()

        bottomnav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.planFragment -> {
                    //val plan2 = PlanFragment.newInstance(1.toString(),2.toString())
                    //myToolbar.title = "Linear Layout"
                    val planfragment = PlanFragment()
//                    val bundle =Bundle()
//                    bundle.putString("Search item",searchvalue)
//                    planfragment.arguments =bundle
                    supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.meContainer, planfragment).commit()


                }
                R.id.bookFragment -> {
                      val bookfragment = BookFragment()
                      //myToolbar.title = "Grid Layout"
                      supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.meContainer, bookfragment).commit()
                }
                R.id.communityFragment -> {
                      val community = CommunityFragment()
                      //myToolbar.title = "Staggered Grid Layout"
                      supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter,R.anim.close).replace(R.id.meContainer, community).addToBackStack("null").commit()
                }

                  else -> {}
              }
              true //tell that this item selector is registered
       }

//        // Default - linear layout manager
//        val fr = LayoutFragment.newInstance(1)
//        myToolbar.title = "Layout Manager"
//        supportFragmentManager.beginTransaction().replace(R.id.lmContainer, fr).commit()

       }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
    //drawer handle items
        when(item.itemId){
            R.id.logout -> {
                val user :String? =null
                myDB.updateCurrentUser(user,0)
                //back to login activity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)


            }

        }

    drawerlayout.closeDrawer(GravityCompat.START)
    return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // inflate the menu into top toolbar
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val search = searchItem?.actionView as SearchView

        // Set input type
        search.inputType = InputType.TYPE_CLASS_TEXT

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    searchvalue =query //save the search value and pass it to explore fragment

//                    val navController: NavController =
//                        findNavController()
//                    navController.run {
//                        popBackStack()
//                        navigate(R.id.planFragment)
//                    }

//                    // Replace the existing PlanFragment with the new one
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                        .replace(R.id.meContainer, PlanFragment())
                        //.detach(PlanFragment())
                        //.attach(PlanFragment())
                        .commit()



//                    val frg = supportFragmentManager.findFragmentById(R.id.meContainer);
//                    final FragmentTransaction ft = supportFragmentManager.beginTransaction();
//                    ft.detach(frg);
//                    ft.attach(frg);
//                    ft.commit();
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


        return super.onCreateOptionsMenu(menu)
    }

    fun getCurrentUser(): String? {
        return currentUser //email
    }

    fun getSearchValue():String{
        return searchvalue
    }

    fun setCurrentLocation(data: String?){
        this.data = data
    }

    fun getCurrentLocation(): String? {
        return data
    }


//    override fun onSaveInstanceState(outState: Bundle) {
//
//        // Save the IDs of selected movies
//        Log.i("USer Saved",currentUser.toString())
//        //val selectedMovieIds = movieListDB.filter {(it.checked == true) }.map { it.id }
//        outState.putString("current user", currentUser)
//
//
//        super.onSaveInstanceState(outState)
//
//    }

//    override fun onResume() {
//        super.onResume()
//        // Fetching the stored data from the SharedPreference
//        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
//        val retrieved_data = sh.getString("current user info", "")
//
//
//        // Setting the fetched data in the EditTexts
//        currentUserFromSaved = retrieved_data
//
//        //Log.i("From login1",currentUser.toString())
//        Log.i("From saved1",currentUserFromSaved.toString())
//
//        if (currentUserFromSaved.isNullOrEmpty()){ //valued from savedsaved
//            println("first loop1")
//
//
//            //getting data from intent login, since there is no saved data
//            currentUser = intent.getStringExtra("Current_User_Name").toString() //email
//            Log.i("From login3",currentUser.toString())
//
//            if (currentUser.isNullOrEmpty() ) { //value from login
//                //back to login activity
//                Log.i("From login2",currentUser.toString())
//                Log.i("From saved2",currentUserFromSaved.toString())
//
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//            }
//            else{
//                Log.i("From login4",currentUser.toString())
//            }
//        } else{
//            currentUser = currentUserFromSaved
//
//
//
//            Log.i("From login1",currentUser.toString())
//            Log.i("From saved1",currentUserFromSaved.toString())
//        }
//
//    }

//    override fun onPause() {
//        super.onPause()
//        // Creating a shared pref object with a file name "MySharedPref" in private mode
//        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
//        val myEdit = sharedPreferences.edit()
//
//        Log.i("USer Stop",currentUser.toString())
//        // write all the data entered by the user in SharedPreference and apply
//        myEdit.putString("current user info", currentUser)
//
//        myEdit.apply()
//    }



}