package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton: Button = findViewById(R.id.loginButton)
        val loginEmail: EditText = findViewById(R.id.loginEmail)
        val loginPassword: EditText = findViewById(R.id.loginPassword)

        loginButton.setOnClickListener {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()

            // Check credentials (for simplicity, using SharedPreferences)
            val sharedPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE)
            val storedEmail = sharedPrefs.getString("email", "")
            val storedPassword = sharedPrefs.getString("password", "")

            if (email == storedEmail && password == storedPassword) {
                val intent = Intent(this, profile::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Wrong Details !", Toast.LENGTH_SHORT).show()
                // Show error (validations should be added)
            }
        }
    }

    fun onSignUpClick(view: View) {
        val intent = Intent(this, signup::class.java)
        startActivity(intent)
    }
}
