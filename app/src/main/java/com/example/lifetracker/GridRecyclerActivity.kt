package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.lifetracker.databinding.ActivityGridRecyclerBinding

class GridRecyclerActivity : AppCompatActivity(), GridAdapter.WorkoutItemListener {
    private lateinit var binding : ActivityGridRecyclerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBackGridRecycler.setOnClickListener {
            var intent = Intent(this, FitnessHomePageActivity::class.java)
            startActivity(intent)
        }
        //using the view model
        val viewModel: WorkoutListViewModel by viewModels()
        viewModel.getWorkouts().observe(this) { workouts ->
            var gridAdapter = GridAdapter(this, workouts, this)
            binding.gridRecyclerView.adapter = gridAdapter
        }
    }
        override fun workoutSelected(workout: Workout) { //loads workout values created into detailed view
            val intent = Intent(this, WorkoutDetailActivity::class.java)
            intent.putExtra("workoutId", workout.workoutId)
            intent.putExtra("exerciseName", workout.exerciseName)
            intent.putExtra("equipment", workout.equipment)
            intent.putExtra("weight", workout.weight.toString())
            intent.putExtra("reps", workout.reps.toString())
            intent.putExtra("sets", workout.sets.toString())
            startActivity(intent)
        }
    }