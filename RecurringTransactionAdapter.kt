package com.example.expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.RecurringTransactions.RecurringTransaction

class RecurringTransactionAdapter(private val transactions: List<RecurringTransaction>) :
    RecyclerView.Adapter<RecurringTransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recurring_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.transactionName.text = transaction.name
        holder.transactionAmount.text = "₹${"%.2f".format(transaction.amount)}"
        holder.transactionType.text = transaction.type
    }

    override fun getItemCount(): Int = transactions.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val transactionName: TextView = view.findViewById(R.id.transactionName)
        val transactionAmount: TextView = view.findViewById(R.id.transactionAmount)
        val transactionType: TextView = view.findViewById(R.id.transactionType)
    }
}
