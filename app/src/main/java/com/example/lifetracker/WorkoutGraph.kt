package com.example.lifetracker

import android.graphics.Color.BLACK
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
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

class WorkoutGraph : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutGraphBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutGraphBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var weightCounter = 0f
        var repsCounter = 0f
        var setsCounter = 0f

        var workoutList = ArrayList<Workout>()

        val user = Firebase.auth.currentUser
        val userId = user?.uid

        val db = FirebaseFirestore.getInstance().collection("workouts")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val workout = document.toObject(Workout::class.java)
                    workoutList.add(workout)
                }

                for (workout in workoutList) {
                    weightCounter += workout.weight!!.toFloat()
                    repsCounter += workout.reps!!.toFloat()
                    setsCounter += workout.sets!!.toFloat()
                }

                val graph = binding.workoutGraph

                val entries: MutableList<BarEntry> = ArrayList()
                entries.add(BarEntry(0f, weightCounter))
                entries.add(BarEntry(1f, repsCounter))
                entries.add(BarEntry(2f, setsCounter))
                val set = BarDataSet(entries, "Total Weight, Reps, Sets Count")
                val data = BarData(set)
                data.setBarWidth(0.7f)
                graph.setData(data)
                graph.setFitBars(true)
                graph.setBackgroundColor(BLACK)

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
            .addOnFailureListener {
                Toast.makeText(this, "Failed retrieving documents.", Toast.LENGTH_LONG).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
