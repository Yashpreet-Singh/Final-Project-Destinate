package com.example.finalprojectdestinate

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [PlanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanFragment : Fragment() {
    // TODO: Rename and change types of parameters


    private lateinit var viewpager: androidx.viewpager2.widget.ViewPager2
    //private lateinit var myDataFromActivity:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_plan, container, false)

        viewpager= view.findViewById(R.id.view_pager)
        val tabcontent = view.findViewById<TabLayout>(R.id.tab_layout)

        viewpager.adapter = VPAdapter(this)

        viewpager.isUserInputEnabled = false //disable swipping

        // Use TabLayoutMediator to set the tab text
        TabLayoutMediator(tabcontent, viewpager) { tab, position ->
            val adapter = viewpager.adapter as VPAdapter
            tab.text = adapter.getTitle(position)
        }.attach()

        // Set the current page (the first fragment of the viewpager)
        viewpager.currentItem = 0



//        val activity: MainActivity? = activity as MainActivity?
//        myDataFromActivity = activity?.getCurrentUser().toString()



        return view



    }

//    fun giveuserData():String{
//        return myDataFromActivity
//    }

//    @Deprecated("Deprecated in Java")
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        // inflate the menu into top toolbar
//
//        //clear exsiting items on tollbar
//        menu.clear()
//
//        inflater.inflate(R.menu.search_menu, menu)
//
//        val searchItem = menu.findItem(R.id.action_search)
//        val search = searchItem?.actionView as SearchView
//
//        // Set input type
//        search.inputType = InputType.TYPE_CLASS_TEXT
//
//        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                if (query != null) {
//                    //searchvalue =query //save the search value and pass it to explore fragment
//
////                    // Replace the existing PlanFragment with the new one
////                    supportFragmentManager.beginTransaction()
////                        .replace(R.id.meContainer, planfragment)
////                        .addToBackStack(null)
////                        .commit()
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//        })
//
//
//        return super.onCreateOptionsMenu(menu,inflater)
//    }




}