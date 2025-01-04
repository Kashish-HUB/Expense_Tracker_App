package com.example.expensetracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signupButton: Button = findViewById(R.id.signupButton)
        val alreadyHaveAccountButton: TextView = findViewById(R.id.loginLink)  // Button to handle already have an account

        signupButton.setOnClickListener {
            // Get user inputs
            val signupEmail: EditText = findViewById(R.id.signupEmail)
            val signupPassword: EditText = findViewById(R.id.signupPassword)
            val email = signupEmail.text.toString()
            val password = signupPassword.text.toString()

            // Save user data (for simplicity, using SharedPreferences)
            val sharedPrefs: SharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString("email", email)
            editor.putString("password", password)
            editor.apply()

            // Show toast message indicating successful signup and redirection to login
            Toast.makeText(this, "Account created! Please log in to continue.", Toast.LENGTH_LONG).show()

            // Redirect to the login page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the signup activity
        }

        alreadyHaveAccountButton.setOnClickListener {
            // Show toast message for already having an account
            Toast.makeText(this, "Redirecting to login page. Please enter your credentials.", Toast.LENGTH_LONG).show()

            // Redirect to the login page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the signup activity
        }
    }
}
