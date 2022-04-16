package com.example.lifetracker

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.lifetracker.databinding.ActivityCaloriesBinding
import com.example.lifetracker.databinding.ActivityFitnessBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CaloriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaloriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaloriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val currentUser = FirebaseAuth.getInstance().uid
        Log.d("Current user", currentUser.toString())

        val db = FirebaseFirestore.getInstance().collection("food")
        val query = db.get().addOnSuccessListener { documents ->

            binding.linearLayout.removeAllViews()

            for (document in documents) {

                // Create food object from db
                val food = document.toObject(Food::class.java)

                // Add data to views

                // Food name
                val textViewFoodName = TextView(this)
                textViewFoodName.text = food.foodName
                textViewFoodName.textSize = 18f
                textViewFoodName.typeface = Typeface.DEFAULT_BOLD

                // Calories
                val textViewFoodCalories = TextView(this)
                textViewFoodCalories.text = food.foodCalories.toString() + " Cal"
                textViewFoodCalories.textSize = 16f

                // Category
                val textViewFoodCategory = TextView(this)
                textViewFoodCategory.text = food.foodCategory
                textViewFoodCategory.textSize = 16f

                // Date consumed
                val textViewDateConsumed = TextView(this)
                textViewDateConsumed.text = food.dateConsumed
                textViewDateConsumed.textSize = 16f

                // Whitespace lol
                val textViewWhiteSpace = TextView(this)
                textViewWhiteSpace.textSize = 8f

                // Add views to layout
                binding.linearLayout.addView(textViewFoodName)
                binding.linearLayout.addView(textViewFoodCalories)
                binding.linearLayout.addView(textViewFoodCategory)
                binding.linearLayout.addView(textViewDateConsumed)
                binding.linearLayout.addView(textViewWhiteSpace)
            }

            // TODO: REMOVE THIS LATER
//            val intent = Intent(this, CaloriesAddNewActivity::class.java);
//            startActivity(intent);

            binding.fabAddNewCalorie.setOnClickListener{
                startActivity(Intent(this, CaloriesAddNewActivity::class.java))
            }

        }

    }
}