package com.example.finalprojectdestinate

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class VPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    //VP adpater for the viewpager to change between different fragmnets
    private val fragmentlist: ArrayList<Fragment> = arrayListOf(ExploreFragment(),GuideFragment(),MemoriesFragment())
    private val titlelist: ArrayList<String> = arrayListOf("Explore","Guide","Travel Memories")


    override fun getItemCount(): Int {
        return 3 //number of fragments
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 ->{
                fragmentlist[0]
            }

            1-> {
                fragmentlist[1]
            }
            2->{
                fragmentlist[2]
            }

            else->{
                fragmentlist[0] //default
            }

        }
    }

    fun getTitle(position: Int): String {
        return titlelist[position]
    }


}