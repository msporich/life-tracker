package com.example.lifetracker

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.lifetracker.databinding.ActivityCaloriesBinding
import com.example.lifetracker.databinding.ActivityFitnessBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CaloriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaloriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaloriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Check for and display toasts
        val toast = intent.getStringExtra("toast")
        if (toast != null) {
            Toast.makeText(this, toast, Toast.LENGTH_LONG).show()
        }

        // Check current user
        val currentUser = FirebaseAuth.getInstance().uid
        Log.d("Current user", currentUser.toString())

        // Create instance of view model
        val viewModel : CaloriesViewModel by viewModels()

        // Get calories list from view model
        viewModel.getCaloriesList().observe(this) { foodList ->

            var totalCaloriesToday = 0;

            for (food in foodList) {

                // Compare date consumed to today's date
                val now = Calendar.getInstance()
                val consumed = Calendar.getInstance()
                consumed.time = food.dateConsumed!!

                val todayDayOfYear = now.get(Calendar.DAY_OF_YEAR)
                val todayYear = now.get(Calendar.YEAR)
                val consumedDayOfYear = consumed.get(Calendar.DAY_OF_YEAR)
                val consumedYear = consumed.get(Calendar.YEAR)

                // Add calories to running total if date consumed is today
                if (todayYear == consumedYear && todayDayOfYear == consumedDayOfYear) {
                    Log.d("match", food.foodCalories.toString())
                    totalCaloriesToday += food.foodCalories!!
                }

                // Add data to views

                // Food name
                val textViewFoodName = TextView(this)
                textViewFoodName.text = food.foodName
                textViewFoodName.textSize = 18f
                textViewFoodName.typeface = Typeface.DEFAULT_BOLD

                // Calories
                val textViewCaloriesAmount = TextView(this)
                textViewCaloriesAmount.text = food.foodCalories.toString() + " Cal"
                textViewCaloriesAmount.textSize = 16f

                // Category
                val textViewFoodCategory = TextView(this)
                textViewFoodCategory.text = food.foodCategory
                textViewFoodCategory.textSize = 16f

                // Date consumed
                val textViewDateConsumed = TextView(this)
                textViewDateConsumed.text = food.dateConsumed.toString()
                textViewDateConsumed.textSize = 16f

                // Whitespace lol
                val textViewWhiteSpace = TextView(this)
                textViewWhiteSpace.textSize = 8f

                // Add views to layout
                binding.linearLayout.addView(textViewFoodName)
                binding.linearLayout.addView(textViewCaloriesAmount)
                binding.linearLayout.addView(textViewFoodCategory)
                binding.linearLayout.addView(textViewDateConsumed)
                binding.linearLayout.addView(textViewWhiteSpace)
            }
            binding.textViewCaloriesToday.text = totalCaloriesToday.toString()
        }

        binding.fabAddNewCalorie.setOnClickListener{
            startActivity(Intent(this, CaloriesAddNewActivity::class.java))
        }

    }

}
