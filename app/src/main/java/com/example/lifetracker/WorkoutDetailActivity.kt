package com.example.lifetracker

import android.content.Intent
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

        val weight = intent.getStringExtra("weight")
        val reps = intent.getStringExtra("reps")
        val sets = intent.getStringExtra("sets")

        binding.buttonBackDetail.setOnClickListener {
            startActivity(Intent(this, GridRecyclerActivity::class.java))
        }

        binding.buttonGraphView.setOnClickListener {
            val intent = Intent(this, WorkoutGraph::class.java)
            intent.putExtra("weight", weight)
            intent.putExtra("reps", reps)
            intent.putExtra("sets", sets)
            startActivity(intent)
        }

        fun workoutSelected(workout: Workout) {
            val intent = Intent(this, WorkoutGraph::class.java)
            intent.putExtra("workoutId", workout.workoutId)
            intent.putExtra("weight", workout.weight)
            intent.putExtra("reps", workout.reps)
            intent.putExtra("sets", workout.sets)
            startActivity(intent)
        }

    }
}