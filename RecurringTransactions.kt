package com.example.expensetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecurringTransactions : AppCompatActivity() {

    private lateinit var recurringTransactionsList: RecyclerView
    private lateinit var addRecurringTransactionButton: Button
    private lateinit var recurringTransactionAdapter: RecurringTransactionAdapter
    private val recurringTransactions = mutableListOf<RecurringTransaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recurring_transactions)

        // Initialize views
        recurringTransactionsList = findViewById(R.id.recurringTransactionsList)
        addRecurringTransactionButton = findViewById(R.id.addRecurringTransactionButton)

        // Set up RecyclerView
        recurringTransactionAdapter = RecurringTransactionAdapter(recurringTransactions)
        recurringTransactionsList.layoutManager = LinearLayoutManager(this)
        recurringTransactionsList.adapter = recurringTransactionAdapter

        // Add New Recurring Transaction Button functionality
        addRecurringTransactionButton.setOnClickListener {
            showAddRecurringTransactionDialog()
        }
    }

    private fun showAddRecurringTransactionDialog() {
        // Inflate dialog view
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_transaction, null)

        val nameInput = dialogView.findViewById<EditText>(R.id.transactionNameInput)
        val amountInput = dialogView.findViewById<EditText>(R.id.transactionAmountInput)
        val typeInput = dialogView.findViewById<EditText>(R.id.transactionTypeInput)

        // Show dialog
        val dialog = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Recurring Transaction")
            .setPositiveButton("Save") { _, _ ->
                val name = nameInput.text.toString()
                val amount = amountInput.text.toString()
                val type = typeInput.text.toString()

                if (name.isNotEmpty() && amount.isNotEmpty() && type.isNotEmpty()) {
                    val transaction = RecurringTransaction(name, amount.toDouble(), type)
                    recurringTransactions.add(transaction)
                    recurringTransactionAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Transaction added!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    data class RecurringTransaction(val name: String, val amount: Double, val type: String)
}
