package com.example.lifetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.lifetracker.databinding.ActivityCaloriesRecyclerBinding
import com.google.firebase.auth.FirebaseAuth

class CaloriesRecyclerActivity : AppCompatActivity(), CaloriesRecyclerViewAdapter.FoodItemListener {

    private lateinit var binding : ActivityCaloriesRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaloriesRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check current user
        val currentUser = FirebaseAuth.getInstance().uid
        Log.d("Current user", currentUser.toString())

        // Check for and display toasts
        val toast = intent.getStringExtra("toast")
        if (toast != null) {
            Toast.makeText(this, toast, Toast.LENGTH_LONG).show()
        }

        val viewModel : CaloriesViewModel by viewModels()
        viewModel.getCaloriesList().observe(this) { foodList ->
            var caloriesRecyclerViewAdapter = CaloriesRecyclerViewAdapter(this, foodList, this)
            binding.caloriesRecyclerView.adapter = caloriesRecyclerViewAdapter
        }

    }

    override fun foodSelected(food: Food) {
        Log.d("Food Selected", food.foodName.toString())
    }

}