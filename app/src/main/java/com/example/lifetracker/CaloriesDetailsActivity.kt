package com.example.lifetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifetracker.databinding.ActivityCaloriesDetailsBinding

class CaloriesDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCaloriesDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaloriesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dateConsumed = intent.getStringExtra("dateConsumed")
        val dateConsumedSbstr1 = dateConsumed?.subSequence(0, 10).toString()
        val dateConsumedSbstr2 = dateConsumed?.subSequence(24, 28).toString()
        val dateConsumedFormatted = "$dateConsumedSbstr1, $dateConsumedSbstr2"

        binding.textViewFoodName.text = intent.getStringExtra("foodName")
        binding.textViewCalories.text = intent.getStringExtra("foodCalories")
        binding.textViewCategory.text = intent.getStringExtra("foodCategory")
        binding.textViewDateConsumed.text = dateConsumedFormatted



    }
}