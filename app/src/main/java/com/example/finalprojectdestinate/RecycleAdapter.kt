package com.example.finalprojectdestinate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class RecycleAdapter (val userInfo: ArrayList<UserData>, val context: Context, val myDB: DatabaseHelper):
    androidx.recyclerview.widget.RecyclerView.Adapter<RecycleAdapter.MovieViewHolder>(){

    var myListener: MyItemClickListener? = null
    // Adapter Listener!!!
    interface MyItemClickListener {
        fun onItemClickedFromAdapter(user: UserData)
    }

    fun setMyItemClickListener ( listener: MyItemClickListener){
        this.myListener = listener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecycleAdapter.MovieViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_post, parent, false)

        return  MovieViewHolder(view)
    }



    override fun onBindViewHolder(holder: RecycleAdapter.MovieViewHolder, position: Int) {
        val userpost= userInfo[position]

        holder.userName.text = userpost.firstname

        Picasso.get().load(userpost.myposts).error(R.mipmap.ic_launcher).into(holder.userPost)

//        // Check if the post is liked (you may have a data structure to track this)
//        val isLiked = userpost.isLiked
//
//        // Set initial background color based on whether the post is liked
//        if (isLiked == true) {
//            holder.likeButton.setBackgroundColor(context.resources.getColor(R.color.blue))
//        } else {
//            holder.likeButton.setBackgroundColor(context.resources.getColor(R.color.))
//        }

        // Set up click listener for the like button
        holder.likeButton.setOnClickListener {
            // Toggle like status (you may have a data structure to track this)
            userpost.isLiked = !userpost.isLiked!!

            // Change background color based on the updated like status
            if (userpost.isLiked!!) {
                holder.likeButton.setBackgroundColor(context.resources.getColor(R.color.blue))
            } else {
                holder.likeButton.setBackgroundColor(context.resources.getColor(R.color.white))
            }

            // Handle additional actions when the like button is clicked
            // (e.g., update database, trigger animations, etc.)
            // Your logic here
        }

    }

    //calculation amount of total posts


    override fun getItemCount(): Int {
        return userInfo.size
    }

    inner class MovieViewHolder(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val userName: TextView = view.findViewById(R.id.user_name) //first name
        val deleteButton: ImageButton = view.findViewById(R.id.delete)
        val userPost: ImageView = view.findViewById(R.id.user_pic)
        val likeButton: ImageButton = view.findViewById(R.id.likebutton)

//        init {
//
//            likeButton.setOnClickListener {
//                if (myListener != null) {
//                    if (bindingAdapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
//                        // Call the listener's onItemClickedFromAdapter method
//                        myListener!!.onItemClickedFromAdapter(userInfo[bindingAdapterPosition])
//                    }
//                }
//
//
//            }
//        }


    }


}