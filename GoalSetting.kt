package com.example.expensetracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GoalSetting : AppCompatActivity() {

    private lateinit var goalNameInput: EditText
    private lateinit var targetAmountInput: EditText
    private lateinit var targetDateInput: EditText
    private lateinit var saveGoalButton: Button
    private lateinit var currentSavingsText: TextView
    private lateinit var savingsAmountInput: EditText
    private lateinit var addSavingsButton: Button
    private lateinit var goalProgressBar: ProgressBar

    private var targetAmount: Double = 0.0
    private var currentSavings: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_setting)

        // Initialize Views
        goalNameInput = findViewById(R.id.goalNameInput)
        targetAmountInput = findViewById(R.id.targetAmountInput)
        targetDateInput = findViewById(R.id.targetDateInput)
        saveGoalButton = findViewById(R.id.saveGoalButton)
        currentSavingsText = findViewById(R.id.currentSavingsText)
        savingsAmountInput = findViewById(R.id.savingsAmountInput)
        addSavingsButton = findViewById(R.id.addSavingsButton)
        goalProgressBar = findViewById(R.id.goalProgressBar)

        // Save Goal Button functionality
        saveGoalButton.setOnClickListener {
            val goalName = goalNameInput.text.toString()
            val targetAmountText = targetAmountInput.text.toString()
            val targetDate = targetDateInput.text.toString()

            if (goalName.isNotEmpty() && targetAmountText.isNotEmpty() && targetDate.isNotEmpty()) {
                targetAmount = targetAmountText.toDouble()
                currentSavings = 0.0
                updateProgress()
                Toast.makeText(this, "Goal saved successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Add Savings Button functionality
        addSavingsButton.setOnClickListener {
            val savingsAmountText = savingsAmountInput.text.toString()
            if (savingsAmountText.isNotEmpty()) {
                val savingsAmount = savingsAmountText.toDouble()
                currentSavings += savingsAmount
                updateProgress()
            } else {
                Toast.makeText(this, "Please enter a valid savings amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Update progress bar and text
    private fun updateProgress() {
        val progress = (currentSavings / targetAmount * 100).toInt()
        goalProgressBar.progress = progress
        currentSavingsText.text = "Current Savings: ₹$currentSavings"
    }
}
