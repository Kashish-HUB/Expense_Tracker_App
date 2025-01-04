package com.example.expensetracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Name EditText
        val nameEditText: EditText = findViewById(R.id.inputName)

        // Currency Spinner
        val currencySpinner: Spinner = findViewById(R.id.currencySpinner)
        val currencyOptions = listOf("USD", "EUR", "INR", "GBP", "AUD")
        val currencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyOptions)
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencySpinner.adapter = currencyAdapter

        // Country Spinner
        val countrySpinner: Spinner = findViewById(R.id.countrySpinner)
        val countryOptions = listOf("USA", "India", "UK", "Australia", "Canada")
        val countryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryOptions)
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = countryAdapter

        // Age EditText
        val ageEditText: EditText = findViewById(R.id.ageEditText)

        // Notifications Switch
        val notificationsSwitch: Switch = findViewById(R.id.notificationsSwitch)

        // Save Preferences Button
        val savePreferencesButton: Button = findViewById(R.id.savePreferencesButton)

        savePreferencesButton.setOnClickListener {
            val userName = nameEditText.text.toString()
            val selectedCurrency = currencySpinner.selectedItem.toString()
            val selectedCountry = countrySpinner.selectedItem.toString()
            val age = ageEditText.text.toString().toIntOrNull()
            val notificationsEnabled = notificationsSwitch.isChecked

            // Validate user input
            if (userName.isNotEmpty() && selectedCurrency.isNotEmpty() && selectedCountry.isNotEmpty() && age != null) {
                // Save preferences in SharedPreferences
                val sharedPrefs: SharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
                val editor = sharedPrefs.edit()

                editor.putString("userName", userName)
                editor.putString("currency", selectedCurrency)
                editor.putString("country", selectedCountry)
                editor.putInt("age", age)
                editor.putBoolean("notificationsEnabled", notificationsEnabled)

                // Apply the changes
                editor.apply()

                // Show confirmation and proceed to the Dashboard
                Toast.makeText(this, "Preferences saved successfully!", Toast.LENGTH_SHORT).show()

                // Redirect to the Dashboard
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish() // Close Profile Setup activity
            } else {
                Toast.makeText(this, "Please fill out all fields correctly.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
