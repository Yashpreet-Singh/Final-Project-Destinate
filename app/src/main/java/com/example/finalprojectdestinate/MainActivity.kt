package com.example.finalprojectdestinate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerlayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)//activating toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        drawerlayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.open_nav, R.string.close_nav)

        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

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
        bottomnav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.plan_action -> {
                    val fr = PlanFragment.newInstance(1.toString(),2.toString())
                    //myToolbar.title = "Linear Layout"
                    supportFragmentManager.beginTransaction().replace(R.id.meContainer, fr).commit()
                }
//                R.id.action_grid -> {
//                    val fr = LayoutFragment.newInstance(2)
//                    myToolbar.title = "Grid Layout"
//                    supportFragmentManager.beginTransaction().replace(R.id.lmContainer, fr).commit()
//                }
//                R.id.action_staggered -> {
//                    val fr = LayoutFragment.newInstance(3)
//                    myToolbar.title = "Staggered Grid Layout"
//                    supportFragmentManager.beginTransaction().replace(R.id.lmContainer, fr).commit()
//                }
            }
        }
//
//        // Default - linear layout manager
//        val fr = LayoutFragment.newInstance(1)
//        myToolbar.title = "Layout Manager"
//        supportFragmentManager.beginTransaction().replace(R.id.lmContainer, fr).commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
        //profile
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // inflate the menu into toolbar
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        //val search = searchItem?.actionView as SearchView

//        // Set input type
//        search.inputType = InputType.TYPE_CLASS_TEXT
//
//        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                val pos = myAdapter.findFirst(query!!) // find the movie and scroll to the position--case insentivie
//                if (pos >= 0) {
//                    recyclerView.smoothScrollToPosition(pos)
//                    //Toast.makeText(context, "Search Movie $query found...", Toast.LENGTH_SHORT).show()
//                } else {
//                    recyclerView.smoothScrollToPosition(0)//Scroll to first movie
//                    Toast.makeText(this@RecycleView, "Search Movie $query not found...", Toast.LENGTH_SHORT).show()
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//        })


        return super.onCreateOptionsMenu(menu)
    }


}