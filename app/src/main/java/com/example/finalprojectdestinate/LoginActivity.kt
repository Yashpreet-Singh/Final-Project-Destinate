package com.example.finalprojectdestinate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    //innitialize database
    lateinit var userListCreatedb: ArrayList<UserData>
    private val myDB: DatabaseHelper by lazy { DatabaseHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.login)
        val createAccountButton: Button = findViewById(R.id.createAccount)

        //intialize dtabase since we nned to check exsisiting users
        myDB.initializeTables() //we get our tables (nned to call onece in full code)
        //get userTable
        userListCreatedb=myDB.getAllUserData()
        Log.d("data", userListCreatedb.toString())

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validateLogin(email, password)) {

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                //maybe add some pause
                // Navigate to main activity also //after susscessful login pass the username to mainactivity
                Intent(this, MainActivity::class.java).also {
                    it.putExtra("Current_User_Name",email)
                    startActivity(it)

                }

                val builder = AlertDialog.Builder(this)
                builder.setCancelable(false)
                builder.setView(R.layout.progress)
                val dialog: AlertDialog = builder.create()
                dialog.show()






            } else {
                // Display error message for invalid credentials
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        createAccountButton.setOnClickListener {
            // Navigate to CreateAccountActivity
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        // Basic validation logic
        if (email.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
            return false
        }

        //check for exsisiting user

        for (user in userListCreatedb){
            if (user.username == email && user.password == password){
                return true
            }
        }




        return false //check no user found
    }
}
