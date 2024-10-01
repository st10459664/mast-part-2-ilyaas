package com.example.st10459664mastpart2

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.st10459664mastpart2.R

data class MenuItem(val name: String, val description: String, val course: String, val price: Double)

class MainActivity : AppCompatActivity() {
    private val menuItems = mutableListOf<MenuItem>()
    private lateinit var listViewMenu: ListView
    private lateinit var tvTotalItems: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewMenu = findViewById(R.id.listViewMenu)
        tvTotalItems = findViewById(R.id.tvTotalItems)

        val btnAddItem: Button = findViewById(R.id.btnAddItem)
        btnAddItem.setOnClickListener { showAddItemDialog() }

        updateMenuList()
    }

    private fun showAddItemDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_item, null)
        val etDishName = dialogView.findViewById<EditText>(R.id.etDishName)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val spinnerCourse = dialogView.findViewById<Spinner>(R.id.spinnerCourse)
        val etPrice = dialogView.findViewById<EditText>(R.id.etPrice)

        val courses = arrayOf("Starters", "Mains", "Desserts")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courses)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Optional
        spinnerCourse.adapter = adapter

        AlertDialog.Builder(this)
            .setTitle("Add Menu Item")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = etDishName.text.toString().trim()
                val description = etDescription.text.toString().trim()
                val course = spinnerCourse.selectedItem.toString()
                val priceString = etPrice.text.toString().trim()
                val price = priceString.toDoubleOrNull() ?: -1.0 // Invalid if parsing fails

                if (name.isNotEmpty() && description.isNotEmpty() && price > 0) {
                    menuItems.add(MenuItem(name, description, course, price))
                    updateMenuList()
                } else {
                    Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateMenuList() {
        val menuListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
            menuItems.map { "${it.name} - ${it.course} - $${it.price}" })
        listViewMenu.adapter = menuListAdapter
        tvTotalItems.text = "Total Menu Items: ${menuItems.size}"
    }
}