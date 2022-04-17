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

        val foodId = intent.getStringExtra("foodId")
        var foodCategoryPos = 0;
        var updateMode = false;
        if (foodId != null) {
            // Update mode
            updateMode = true;
            Log.d("Update mode", updateMode.toString())
            when {
                intent.getStringExtra("foodCategory") == "Breakfast" -> {
                    foodCategoryPos = 1
                }
                intent.getStringExtra("foodCategory") == "Lunch" -> {
                    foodCategoryPos = 2
                }
                intent.getStringExtra("foodCategory") == "Dinner" -> {
                    foodCategoryPos = 3
                }
                intent.getStringExtra("foodCategory") == "Snack" -> {
                    foodCategoryPos = 4
                }
                else -> {
                    Log.w("Invalid", "Something went wrong")
                }
            }
            binding.editTextFoodName.setText(intent.getStringExtra("foodName"))
            binding.editTextCaloriesAmount.setText(intent.getStringExtra("foodCalories"))
            binding.spinnerFoodCategory.setSelection(foodCategoryPos)
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

                    val db = FirebaseFirestore.getInstance().collection("food")
                    val intent = Intent(this, CaloriesRecyclerActivity::class.java)

                    if (updateMode) {
                        food.id = foodId
                        db
                            .document(food.id!!)
                            .update("foodName", food.foodName, "foodCalories", food.foodCalories, "foodCategory", food.foodCategory, "dateConsumed", food.dateConsumed)
                            .addOnSuccessListener {
                                Log.d("Database", "Calorie data updated")
                                intent.putExtra("toast", "Calorie data updated")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Database", "Failed to update", e)
                                intent.putExtra("toast", "Failed to update")
                            }
                        startActivity(intent)
                    }
                    else {
                        // Set owner
                        val currentUser = FirebaseAuth.getInstance().uid
                        food.ownerId = currentUser

                        // Get unique id from Firestore
                        food.id = db.document().id

                        // Create new record
                        db
                            .document(food.id!!)
                            .set(food)
                            .addOnSuccessListener {
                                // Redirect to main calorie tracker activity and display toast
                                Log.i("Database", "Calorie data added")
                                intent.putExtra("toast", "Calorie data added")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Database", "Failed to add data")
                                intent.putExtra("toast", "Failed to add data")
                            }
                        startActivity(intent)
                    }
                }
            }
        }
    }
}