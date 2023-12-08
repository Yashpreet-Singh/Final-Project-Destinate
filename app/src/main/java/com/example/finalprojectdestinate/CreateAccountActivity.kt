package com.example.finalprojectdestinate

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var createAccountButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_login)

        // Initialize UI components
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirm_password)
        dobEditText = findViewById(R.id.dob)
        createAccountButton = findViewById(R.id.create_account)

        // Set up the date picker for DOB field
        dobEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // Set up the create account button
        createAccountButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            val dob = dobEditText.text.toString().trim()

            if (validateAccountCreation(email, password, confirmPassword, dob)) {
                // TODO: Implement account creation logic
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()

                // TODO: Navigate to the next screen or perform further operations
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

    private fun validateAccountCreation(email: String, password: String, confirmPassword: String, dob: String): Boolean {
        // Basic validation logic
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || dob.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
