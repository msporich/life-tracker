package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifetracker.databinding.ActivityFitnessHomePageBinding

class FitnessHomePageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFitnessHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitnessHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //buttons for changing activities
        binding.buttonViewExercises.setOnClickListener {
            var intent = Intent(this, GridRecyclerActivity::class.java)
            startActivity(intent)
        }
        binding.buttonRegisterExercise.setOnClickListener {
            var intent = Intent(this, FitnessActivity::class.java)
            startActivity(intent)
        }
        binding.buttonGoBack.setOnClickListener {
            var intent = Intent(this, ModulesActivity::class.java)
            startActivity(intent)
        }
    }
}