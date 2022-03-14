package com.example.lifetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifetracker.databinding.ActivityWorkoutDetailBinding

class WorkoutDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWorkoutDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //gets the workout values
        binding.txtViewExerciseDetail.text = intent.getStringExtra("exerciseName")
        binding.txtViewEquipmentDetail.text = intent.getStringExtra("equipment")
        binding.txtViewWeightDetail.text = intent.getStringExtra("weight")
        binding.txtViewRepsDetail.text = intent.getStringExtra("reps")
        binding.txtViewSetsDetail.text = intent.getStringExtra("sets")
    }
}