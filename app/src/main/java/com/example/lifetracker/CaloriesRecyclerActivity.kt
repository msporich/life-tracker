package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.lifetracker.databinding.ActivityCaloriesRecyclerBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class CaloriesRecyclerActivity : AppCompatActivity(), CaloriesRecyclerViewAdapter.FoodItemListener {

    private lateinit var binding : ActivityCaloriesRecyclerBinding
    private lateinit var saveData: SaveData

    override fun onCreate(savedInstanceState: Bundle?) {
        saveData = SaveData(this)
        if (saveData.loadDarkModeState() == true) {
            setTheme(R.style.Theme_LifeTrackerNight)
        } else {
            setTheme(R.style.Theme_LifeTracker)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityCaloriesRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Check current user
        val currentUser = FirebaseAuth.getInstance().uid

        // Check for and display toasts
        val toast = intent.getStringExtra("toast")
        if (toast != null) {
            Toast.makeText(this, toast, Toast.LENGTH_LONG).show()
        }

        val viewModel : CaloriesViewModel by viewModels()
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
                    totalCaloriesToday += food.foodCalories!!
                }
            }

            var caloriesRecyclerViewAdapter = CaloriesRecyclerViewAdapter(this, foodList, this)
            binding.caloriesRecyclerView.adapter = caloriesRecyclerViewAdapter
            binding.textViewCaloriesToday.text = totalCaloriesToday.toString()
        }

        // Add calorie data button
        binding.fabAddNewCalorie.setOnClickListener{
            startActivity(Intent(this, CaloriesAddNewActivity::class.java))
        }

    }

    override fun foodSelected(food: Food) {
        val intent = Intent(this, CaloriesDetailsActivity::class.java)
        intent.putExtra("foodId", food.id)
        intent.putExtra("foodName", food.foodName)
        intent.putExtra("foodCalories", food.foodCalories.toString())
        intent.putExtra("foodCategory", food.foodCategory)
        intent.putExtra("dateConsumed", food.dateConsumed.toString())
        startActivity(intent)
    }

}