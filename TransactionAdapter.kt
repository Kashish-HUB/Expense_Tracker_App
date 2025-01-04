package com.example.expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.ExpenseTracking.Transaction

class TransactionAdapter(
    private val transactions: MutableList<Transaction>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description: TextView = itemView.findViewById(R.id.description)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val type: TextView = itemView.findViewById(R.id.type)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.description.text = transaction.description
        holder.amount.text = "$${transaction.amount}"
        holder.type.text = transaction.type
        holder.deleteButton.setOnClickListener { onDelete(position) }
    }

    override fun getItemCount(): Int = transactions.size
}
