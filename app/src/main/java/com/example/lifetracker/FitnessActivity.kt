package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifetracker.databinding.ActivityFitnessBinding
import com.example.lifetracker.databinding.ActivityMoodBinding

class FitnessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFitnessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.floatingActionButton.setOnClickListener {
            var intent = Intent(this, ModulesActivity::class.java)
            startActivity(intent)
        }
    }
}