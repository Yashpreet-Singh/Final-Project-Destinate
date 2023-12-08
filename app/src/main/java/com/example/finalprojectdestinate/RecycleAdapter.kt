package com.example.finalprojectdestinate

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class RecycleAdapter (val userInfo: ArrayList<UserData>, val context: Context, val myDB: DatabaseHelper):
    androidx.recyclerview.widget.RecyclerView.Adapter<RecycleAdapter.UserViewHolder>(){

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
    ): RecycleAdapter.UserViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_post, parent, false)

        return  UserViewHolder(view)
    }



    override fun onBindViewHolder(holder: RecycleAdapter.UserViewHolder, position: Int) {
        val userpost= userInfo[position]

        holder.userName.text = userpost.firstname

        Picasso.get().load(userpost.myposts).error(R.mipmap.ic_launcher).into(holder.userPost)

        holder.description.text =userpost.title

        // Check if the post is liked (you may have a data structure to track this)
        val isLiked = userpost.isLiked

        Log.i("current value in on bind",isLiked.toString())

        // Set initial background color based on whether the post is liked
        if (isLiked == true) {
            holder.likeButton.setBackgroundColor(context.resources.getColor(R.color.blue))
        } else {
            holder.likeButton.setBackgroundColor(context.resources.getColor(R.color.white))
        }

//        // Set up click listener for the like button
//        holder.likeButton.setOnClickListener {
//            // Toggle like status (you may have a data structure to track this)
//            userpost.isLiked = !userpost.isLiked!!
//            userInfo[position].isLiked = userpost.isLiked
//
//            // Change background color based on the updated like status
//            if (userpost.isLiked!!) {
//                holder.likeButton.setBackgroundColor(context.resources.getColor(R.color.blue))
//                myDB.updateLikedMovie(userInfo[position]) //sending type UserData of postion
//            } else {
//                holder.likeButton.setBackgroundColor(context.resources.getColor(R.color.white))
//            }
//        }




    }




    override fun getItemCount(): Int {
        return userInfo.size
    }

    inner class UserViewHolder(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val userName: TextView = view.findViewById(R.id.user_name) //first name
        val deleteButton: ImageButton = view.findViewById(R.id.delete)
        val description : TextView = view.findViewById(R.id.description)
        val userPost: ImageView = view.findViewById(R.id.user_pic)
        val likeButton: ImageButton = view.findViewById(R.id.likebutton)

        init {

            likeButton.setOnClickListener {
                if (myListener != null) {
                    if (adapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {

                        //userpost.isLiked = !userpost.isLiked!!
                        userInfo[adapterPosition].isLiked = !userInfo[adapterPosition].isLiked!!
                        // Call the listener's onItemClickedFromAdapter method
                        //myListener!!.onLikeClickedFromAdapter(it,userInfo[bindingAdapterPosition])
                        val user = userInfo[adapterPosition]

                        if (user.isLiked == true) {
                            context.resources?.let { likeButton.setBackgroundColor(it.getColor(R.color.blue)) }

                            Log.i("on adpater",user.toString())
                            //update like in database
                            myDB.updateLikedUser(user) //sending type UserData of postion

                        } else {
                            context.resources?.let { likeButton.setBackgroundColor(it.getColor(R.color.white)) }

                            //update database
                            myDB.updateLikedUser(user)
                        }
                    }
                }


            }
        }


    }




}