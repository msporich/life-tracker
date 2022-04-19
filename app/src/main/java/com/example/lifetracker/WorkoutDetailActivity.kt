package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifetracker.databinding.ActivityWorkoutDetailBinding

class WorkoutDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWorkoutDetailBinding
    private lateinit var saveData: SaveData
    override fun onCreate(savedInstanceState: Bundle?) {
        saveData = SaveData(this)
        if (saveData.loadDarkModeState() == true) {
            setTheme(R.style.Theme_LifeTrackerNight)
        } else {
            setTheme(R.style.Theme_LifeTracker)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //gets the workout values
        binding.txtViewExerciseDetail.text = intent.getStringExtra("exerciseName")
        binding.txtViewEquipmentDetail.text = intent.getStringExtra("equipment")
        binding.txtViewWeightDetail.text = intent.getStringExtra("weight")
        binding.txtViewRepsDetail.text = intent.getStringExtra("reps")
        binding.txtViewSetsDetail.text = intent.getStringExtra("sets")

        binding.buttonBackDetail.setOnClickListener {
            startActivity(Intent(this, GridRecyclerActivity::class.java))
        }
    }
}