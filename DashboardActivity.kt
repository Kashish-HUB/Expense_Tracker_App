package com.example.expensetracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.widget.ImageView


class DashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        // Set up the toolbar with hamburger icon
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up navigation header
        setupNavigationHeader()

        // Handle Navigation Item Clicks
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
                R.id.menu_expenses -> Toast.makeText(this, "Expenses clicked", Toast.LENGTH_SHORT).show()
                R.id.menu_settings -> Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                R.id.menu_logout -> {
                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        // Set click listeners for service items
        val service1TextView: TextView = findViewById(R.id.textview1)
        val service1ImageView: ImageView = findViewById(R.id.image1)
        val service2TextView: TextView = findViewById(R.id.textview2)
        val service2ImageView: ImageView = findViewById(R.id.image2)
        val service3TextView: TextView = findViewById(R.id.textview3)
        val service3ImageView: ImageView = findViewById(R.id.image3)
        val service4TextView: TextView = findViewById(R.id.textview4)
        val service4ImageView: ImageView = findViewById(R.id.image4)
        val service5TextView: TextView = findViewById(R.id.textview5)
        val service5ImageView: ImageView = findViewById(R.id.image5)
        val service6TextView: TextView = findViewById(R.id.textview6)
        val service6ImageView: ImageView = findViewById(R.id.image6)

        // Set onClick listeners to open respective activities
        service1TextView.setOnClickListener { openServicePage(1) }
        service1ImageView.setOnClickListener { openServicePage(1) }

        service2TextView.setOnClickListener { openServicePage(2) }
        service2ImageView.setOnClickListener { openServicePage(2) }

        service3TextView.setOnClickListener { openServicePage(3) }
        service3ImageView.setOnClickListener { openServicePage(3) }

        service4TextView.setOnClickListener { openServicePage(4) }
        service4ImageView.setOnClickListener { openServicePage(4) }

        service5TextView.setOnClickListener { openServicePage(5) }
        service5ImageView.setOnClickListener { openServicePage(5) }

        service6TextView.setOnClickListener { openServicePage(6) }
        service6ImageView.setOnClickListener { openServicePage(6) }
    }

    private fun openServicePage(serviceId: Int) {
        val intent: Intent = when (serviceId) {
            1 -> Intent(this, ExpenseTracking::class.java)
            2 -> Intent(this, BudgetManagement::class.java)
            3 -> Intent(this, FinancialReports::class.java)
            4 -> Intent(this, RecurringTransactions::class.java)
            5 -> Intent(this, DataBackup::class.java)
            6 -> Intent(this, GoalSetting::class.java)
            else -> throw IllegalArgumentException("Invalid service ID")
        }
        startActivity(intent)
    }

    private fun setupNavigationHeader() {
        val sharedPrefs: SharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE)
        val userName = sharedPrefs.getString("userName", "User")

        // Access the header layout from NavigationView
        val headerView: View = navigationView.getHeaderView(0)
        val nameTextView: TextView = headerView.findViewById(R.id.navHeaderName)

        // Set user name in the header
        nameTextView.text = "Welcome, $userName"
    }
}
