package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.lifetracker.databinding.ActivityGridRecyclerBinding

class GridRecyclerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGridRecyclerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBackGridRecycler.setOnClickListener {
            var intent = Intent(this, FitnessHomePageActivity::class.java)
            startActivity(intent)
        }

        val viewModel: WorkoutListViewModel by viewModels()
        viewModel.getWorkouts().observe(this) { workouts ->
            var gridAdapter = GridAdapter(this, workouts)
            binding.gridRecyclerView.adapter = gridAdapter
        }
    }
}