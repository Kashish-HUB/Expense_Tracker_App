package com.example.expensetracker

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class BudgetManagement : AppCompatActivity() {

    private lateinit var budgetSummary: TextView
    private lateinit var descriptionInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var addIncomeButton: Button
    private lateinit var addExpenseButton: Button
    private lateinit var transactionRecyclerView: RecyclerView
    private lateinit var goalInput: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var goalSummary: TextView

    private val transactions = mutableListOf<Transaction>()
    private var totalBudget = 0.0
    private var budgetGoal = 0.0
    private lateinit var budgetTransactionAdapter: BudgetTransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_management)

        // Initialize views
        budgetSummary = findViewById(R.id.budgetSummary)
        descriptionInput = findViewById(R.id.descriptionInput)
        amountInput = findViewById(R.id.amountInput)
        addIncomeButton = findViewById(R.id.addIncomeButton)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        transactionRecyclerView = findViewById(R.id.transactionRecyclerView)
        goalInput = findViewById(R.id.goalInput)
        progressBar = findViewById(R.id.progressBar)
        goalSummary = findViewById(R.id.goalSummary)

        // Set up RecyclerView with the new adapter name
        budgetTransactionAdapter = BudgetTransactionAdapter(transactions) { position ->
            deleteTransaction(position)
        }
        transactionRecyclerView.layoutManager = LinearLayoutManager(this)
        transactionRecyclerView.adapter = budgetTransactionAdapter

        // Load saved data (transactions and goal)
        loadTransactions()

        // Add Income Button functionality
        addIncomeButton.setOnClickListener {
            addTransaction("Income")
        }

        // Add Expense Button functionality
        addExpenseButton.setOnClickListener {
            addTransaction("Expense")
        }

        // Set Budget Goal functionality
        goalInput.setOnFocusChangeListener { _, _ ->
            val goal = goalInput.text.toString().toDoubleOrNull()
            if (goal != null && goal > 0) {
                budgetGoal = goal
                updateGoalSummary()
                updateProgressBar()
                saveGoal()
            }
        }
    }

    private fun addTransaction(type: String) {
        val description = descriptionInput.text.toString()
        val amount = amountInput.text.toString().toDoubleOrNull()

        if (description.isNotEmpty() && amount != null) {
            val transaction = Transaction(description, amount, type)
            transactions.add(transaction)
            totalBudget += if (type == "Income") amount else -amount
            updateBudgetSummary()
            saveTransactions()
            budgetTransactionAdapter.notifyDataSetChanged()
            clearInputFields()
        } else {
            Toast.makeText(this, "Please enter valid details.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTransaction(position: Int) {
        val transaction = transactions[position]
        totalBudget -= if (transaction.type == "Income") transaction.amount else -transaction.amount
        transactions.removeAt(position)
        updateBudgetSummary()
        saveTransactions()
        budgetTransactionAdapter.notifyItemRemoved(position)
    }

    private fun updateBudgetSummary() {
        budgetSummary.text = "Current Budget: $%.2f".format(totalBudget)
    }

    private fun updateGoalSummary() {
        goalSummary.text = "Budget Goal: $%.2f".format(budgetGoal)
    }

    private fun updateProgressBar() {
        if (budgetGoal > 0) {
            val progress = ((totalBudget / budgetGoal) * 100).toInt()
            progressBar.progress = progress
            if (totalBudget >= budgetGoal) {
                Toast.makeText(this, "Congratulations! You've reached your goal!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearInputFields() {
        descriptionInput.text.clear()
        amountInput.text.clear()
    }

    private fun saveTransactions() {
        val file = File(filesDir, "transactions.json")
        file.writeText(Gson().toJson(transactions))
    }

    private fun loadTransactions() {
        val file = File(filesDir, "transactions.json")
        if (file.exists()) {
            val json = file.readText()
            val type = object : TypeToken<MutableList<Transaction>>() {}.type
            val savedTransactions: MutableList<Transaction> = Gson().fromJson(json, type)
            transactions.addAll(savedTransactions)
            totalBudget = transactions.filter { it.type == "Income" }.sumOf { it.amount } -
                    transactions.filter { it.type == "Expense" }.sumOf { it.amount }
            updateBudgetSummary()
        }

        // Load goal
        val goalFile = File(filesDir, "budget_goal.json")
        if (goalFile.exists()) {
            val jsonGoal = goalFile.readText()
            budgetGoal = jsonGoal.toDoubleOrNull() ?: 0.0
            goalInput.setText(budgetGoal.toString())
            updateGoalSummary()
            updateProgressBar()
        }
    }

    private fun saveGoal() {
        val goalFile = File(filesDir, "budget_goal.json")
        goalFile.writeText(budgetGoal.toString())
    }

    data class Transaction(val description: String, val amount: Double, val type: String)
}
