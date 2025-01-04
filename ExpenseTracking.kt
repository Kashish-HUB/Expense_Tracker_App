package com.example.expensetracker

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExpenseTracking : AppCompatActivity() {

    // UI Components
    private lateinit var totalIncomeTextView: TextView
    private lateinit var totalExpensesTextView: TextView
    private lateinit var budgetProgressBar: ProgressBar
    private lateinit var transactionDescriptionEditText: EditText
    private lateinit var transactionAmountEditText: EditText
    private lateinit var transactionTypeSpinner: Spinner
    private lateinit var addTransactionButton: Button
    private lateinit var transactionRecyclerView: RecyclerView

    // Data
    private val transactions = mutableListOf<Transaction>()
    private var totalIncome = 0.0
    private var totalExpenses = 0.0
    private var budgetLimit = 6000.0 // Default budget limit

    // SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_tracking)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("ExpenseTrackerPrefs", Context.MODE_PRIVATE)

        // Initialize UI Components
        totalIncomeTextView = findViewById(R.id.totalIncome)
        totalExpensesTextView = findViewById(R.id.totalExpenses)
        budgetProgressBar = findViewById(R.id.budgetProgressBar)
        transactionDescriptionEditText = findViewById(R.id.transactionDescription)
        transactionAmountEditText = findViewById(R.id.transactionAmount)
        transactionTypeSpinner = findViewById(R.id.transactionType)
        addTransactionButton = findViewById(R.id.addTransactionButton)
        transactionRecyclerView = findViewById(R.id.transactionRecyclerView)

        // Set up Spinner
        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.transaction_types,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        transactionTypeSpinner.adapter = spinnerAdapter

        // Set up RecyclerView
        val transactionAdapter = TransactionAdapter(transactions) { position -> deleteTransaction(position) }
        transactionRecyclerView.layoutManager = LinearLayoutManager(this)
        transactionRecyclerView.adapter = transactionAdapter

        // Load saved transactions
        loadTransactions()

        // Add Transaction Button Click Listener
        addTransactionButton.setOnClickListener {
            addTransaction(transactionAdapter)
        }

        // Initial UI Setup
        updateUI()
    }

    private fun addTransaction(adapter: TransactionAdapter) {
        val description = transactionDescriptionEditText.text.toString()
        val amountText = transactionAmountEditText.text.toString()
        val type = transactionTypeSpinner.selectedItem.toString()

        if (description.isEmpty() || amountText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        val transaction = Transaction(description, amount, type)
        transactions.add(transaction)

        if (type == "Income") {
            totalIncome += amount
        } else if (type == "Expense") {
            totalExpenses += amount
        }

        updateUI()
        adapter.notifyDataSetChanged()

        // Save transactions
        saveTransactions()

        // Clear Input Fields
        transactionDescriptionEditText.text.clear()
        transactionAmountEditText.text.clear()
    }

    private fun deleteTransaction(position: Int) {
        val transaction = transactions[position]
        if (transaction.type == "Income") {
            totalIncome -= transaction.amount
        } else if (transaction.type == "Expense") {
            totalExpenses -= transaction.amount
        }
        transactions.removeAt(position)

        updateUI()

        // Save transactions
        saveTransactions()
    }

    private fun updateUI() {
        totalIncomeTextView.text = "Total Income: $$totalIncome"
        totalExpensesTextView.text = "Total Expenses: $$totalExpenses"

        val progress = if (budgetLimit > 0) {
            (totalExpenses / budgetLimit * 100).toInt()
        } else {
            0
        }
        budgetProgressBar.progress = progress
    }

    private fun saveTransactions() {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(transactions)
        editor.putString("transactions", json)
        editor.putFloat("totalIncome", totalIncome.toFloat())
        editor.putFloat("totalExpenses", totalExpenses.toFloat())
        editor.apply()
    }

    private fun loadTransactions() {
        val json = sharedPreferences.getString("transactions", null)
        val type = object : TypeToken<MutableList<Transaction>>() {}.type
        val savedTransactions = gson.fromJson<MutableList<Transaction>>(json, type) ?: mutableListOf()

        transactions.addAll(savedTransactions)
        totalIncome = sharedPreferences.getFloat("totalIncome", 0.0f).toDouble()
        totalExpenses = sharedPreferences.getFloat("totalExpenses", 0.0f).toDouble()
    }

    data class Transaction(val description: String, val amount: Double, val type: String)
}
