package com.example.lifetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifetracker.databinding.ActivityCaloriesAddNewBinding

class CaloriesAddNewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCaloriesAddNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaloriesAddNewBinding.inflate(layoutInflater);
        setContentView(binding.root);

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}