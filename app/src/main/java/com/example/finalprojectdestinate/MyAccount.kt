package com.example.finalprojectdestinate

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mikhaellopez.circularimageview.CircularImageView
import org.w3c.dom.Text

class MyAccount : AppCompatActivity() {

    lateinit var userListCreatedb: ArrayList<UserData>
    private val myDB: DatabaseHelper by lazy { DatabaseHelper(this) }



    private var currentUserFirstname : String? = null
    private  var currentUserLastName : String? = null

    private var currentUserImage : ByteArray? = null
    private var currentUser : String? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)

        myDB.initializeTables()
        currentUser=myDB.getCurrentUser()
        userListCreatedb=myDB.getAllUserData()


        val firstname: TextView =findViewById(R.id.inputfirstname1)
        val lastname: TextView = findViewById(R.id.inputlastname1)
        val profile : ImageView =findViewById(R.id.userImageView1)
        val email : TextView =findViewById(R.id.email1)

        for ( user in userListCreatedb){
            if(user.username == currentUser){
                currentUserFirstname = user.firstname.toString()
                currentUserLastName = user.lastname.toString()
                currentUserImage = user.profileImg
            }
        }

        firstname.text = currentUserFirstname
        lastname.text = currentUserLastName

        email.text= currentUser

        val byteArray = currentUserImage

        if (byteArray!= null) {

            val bitmap: Bitmap? = BitmapFactory.decodeByteArray(
                byteArray, 0,
                byteArray?.size!!
            )

            if (bitmap != null) {
                profile.setImageBitmap(bitmap)
            } else {
                if (currentUserFirstname == "Yash") {
                    profile.setImageResource(R.drawable.yash)
                }
                if (currentUserFirstname == "Tabish") {
                    profile.setImageResource(R.drawable.tabish)
                }

            }
        }






    }
}