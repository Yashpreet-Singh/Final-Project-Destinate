package com.example.finalprojectdestinate

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.util.Calendar

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var createAccountButton: Button

    private lateinit var imageUploadButton: ImageView
    private val PICK_IMAGE_REQUEST = 1

    private lateinit var  imageByteArray :ByteArray



    //innitialize database
    //lateinit var userListCreatedb: ArrayList<UserData>
    private val myDB: DatabaseHelper by lazy { DatabaseHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_login)

        // Initialize UI components
        emailEditText = findViewById(R.id.email)
        firstNameEditText = findViewById(R.id.inputfirstname)
        lastNameEditText = findViewById(R.id.inputlastname)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirm_password)
        dobEditText = findViewById(R.id.dob)
        createAccountButton = findViewById(R.id.create_account)

        imageUploadButton =findViewById(R.id.userImageView)

        //intialize dtabase
        //myDB.initializeTables() //we get our tables
        //get userTable
        //userListCreatedb=myDB.getAllUserData()

        // Set up the date picker for DOB field
        dobEditText.setOnClickListener {
            showDatePickerDialog()
        }

        imageUploadButton.setOnClickListener {
            val gallery = Intent()
            gallery.type = "image/*"
            gallery.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(gallery, "Select a picture"), PICK_IMAGE_REQUEST)
            //val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }


        // Set up the create account button
        createAccountButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            val dob = dobEditText.text.toString().trim()
            val firstname = firstNameEditText.text.toString().trim()
            val lastname = lastNameEditText.text.toString().trim()

            if (validateAccountCreation(email, password, confirmPassword, dob,firstname,lastname)) {
                // whathappens after account creation ->add this data in tables


                myDB.addNewUser(email, password, firstname, lastname, imageByteArray)//give image

                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()

                // Go to mainactivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)


            } else {
                // Error handling
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show()
            }
        }








    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"
            dobEditText.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun validateAccountCreation(email: String, password: String, confirmPassword: String, dob: String, firstname :String,lastname:String): Boolean {
        // Basic validation logic
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { imageUri ->
                val imageBitmap = loadBitmapFromUri(imageUri)
                imageByteArray = convertBitmapToByteArray(imageBitmap)

                // Store image in the database


                //dbHelper.addImage(imageByteArray)
            }
        }

        }

    private fun loadBitmapFromUri(uri: android.net.Uri): Bitmap {
        return BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }


    }

