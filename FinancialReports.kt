package com.example.expensetracker

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FinancialReports: AppCompatActivity() {

    private lateinit var incomeInput: EditText
    private lateinit var expenseInput: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultsTitle: TextView
    private lateinit var netBalance: TextView
    private lateinit var advice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financial_reports)

        // Initialize Views
        incomeInput = findViewById(R.id.incomeInput)
        expenseInput = findViewById(R.id.expenseInput)
        calculateButton = findViewById(R.id.calculateButton)
        resultsTitle = findViewById(R.id.resultsTitle)
        netBalance = findViewById(R.id.netBalance)
        advice = findViewById(R.id.advice)

        // Set Calculate Button Listener
        calculateButton.setOnClickListener {
            calculateFinancialReport()
        }
    }

    private fun calculateFinancialReport() {
        val income = incomeInput.text.toString().toDoubleOrNull()
        val expense = expenseInput.text.toString().toDoubleOrNull()

        if (income == null || expense == null) {
            Toast.makeText(this, "Please enter valid values!", Toast.LENGTH_SHORT).show()
            return
        }

        val balance = income - expense
        val adviceMessage = when {
            balance > 0 -> "Great job! You’re saving money. Consider investing your surplus."
            balance < 0 -> "You’re spending more than you earn. Review your expenses and create a budget."
            else -> "Your income matches your expenses. Aim to save a portion of your income."
        }

        // Display Results
        resultsTitle.visibility = View.VISIBLE
        netBalance.visibility = View.VISIBLE
        advice.visibility = View.VISIBLE

        netBalance.text = "Net Balance: $%.2f".format(balance)
        advice.text = "Advice: $adviceMessage"
    }
}
