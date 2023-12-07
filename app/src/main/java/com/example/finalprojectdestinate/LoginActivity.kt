package com.example.finalprojectdestinate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.login)
        val createAccountTextView: TextView = findViewById(R.id.createAccount)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validateLogin(email, password)) {
                // Perform login operation
                // TODO: Integrate with actual authentication logic
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                // TODO: Navigate to the next activity after successful login
            } else {
                // Display error message for invalid credentials
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        createAccountTextView.setOnClickListener {
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
        // TODO: Add additional validation rules (e.g., email format, password strength) if necessary
        return true
    }
}
