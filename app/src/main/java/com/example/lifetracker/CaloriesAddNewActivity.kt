package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lifetracker.databinding.ActivityCaloriesAddNewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CaloriesAddNewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCaloriesAddNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaloriesAddNewBinding.inflate(layoutInflater);
        setContentView(binding.root);
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Check for and display toasts
        val toast = intent.getStringExtra("toast")
        if (toast != null) {
            Toast.makeText(this, toast, Toast.LENGTH_LONG).show()
        }

        // Listen for save button click
        binding.fabSaveNewCalorie.setOnClickListener {

            when {
                binding.editTextFoodName.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Please enter food name", Toast.LENGTH_LONG).show()
                }
                binding.editTextCaloriesAmount.toString().isEmpty() -> {
                    Toast.makeText(this, "Please enter amount of calories", Toast.LENGTH_LONG).show()
                }
                binding.spinnerFoodCategory.selectedItemPosition == 0 -> {
                    Toast.makeText(this, "Please select a category", Toast.LENGTH_LONG).show()
                }
                else -> {

                    // Assemble Food object
                    val food = Food()
                    val dateConsumedYear = binding.datePickerDate.year
                    val dateConsumedMonth = binding.datePickerDate.month
                    val dateConsumerDay = binding.datePickerDate.dayOfMonth
                    val dateConsumed = Calendar.getInstance()
                    dateConsumed.set(dateConsumedYear, dateConsumedMonth, dateConsumerDay, 0, 0, 0)
                    food.foodName = binding.editTextFoodName.text.toString()
                    food.foodCalories = binding.editTextCaloriesAmount.text.toString().toInt()
                    food.foodCategory = binding.spinnerFoodCategory.selectedItem.toString()
                    food.dateConsumed = dateConsumed.time

                    // Set owner
                    val currentUser = FirebaseAuth.getInstance().uid
                    food.ownerId = currentUser

                    // Get unique id from Firestore
                    val db = FirebaseFirestore.getInstance().collection("food")
                    food.id = db.document().id

                    // Create new record
                    db.document(food.id!!).set(food)

                    // Redirect to main calorie tracker activity and display toast
                    val intent = Intent(this, CaloriesRecyclerActivity::class.java)
                    intent.putExtra("toast", "Calorie data added")
                    startActivity(intent)

                }
            }

        }

    }
}