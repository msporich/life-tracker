package com.example.lifetracker

import android.content.Intent
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lifetracker.databinding.ActivityWorkoutGraphBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class WorkoutGraph : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutGraphBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var weightCounter = intent.getStringExtra("weight")?.toFloat()
        var repsCounter = intent.getStringExtra("reps")?.toFloat()
        var setsCounter = intent.getStringExtra("sets")?.toFloat()

        val graph = binding.workoutGraph

        val entries: MutableList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, weightCounter!!))
        entries.add(BarEntry(1f, repsCounter!!))
        entries.add(BarEntry(2f, setsCounter!!))
        val set = BarDataSet(entries, "Total Weight, Reps, Sets Count")
        val data = BarData(set)
        data.setBarWidth(0.7f)
        graph.setData(data)
        graph.setFitBars(true)
        graph.setBackgroundColor(WHITE)

        val workoutLabels = arrayOf("Weight", "Reps", "Sets")

        val formatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return workoutLabels.get(value.toInt())
            }
        }

        val xAxis = graph.getXAxis()
        xAxis.setGranularity(1f)
        xAxis.setValueFormatter(formatter)
        graph.invalidate()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
