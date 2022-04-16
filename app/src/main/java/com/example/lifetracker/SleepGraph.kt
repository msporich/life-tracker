package com.example.lifetracker

import android.content.Intent
import android.graphics.Color.WHITE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lifetracker.databinding.ActivitySleepGraphBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.components.AxisBase

class SleepGraph : AppCompatActivity() {
    private lateinit var binding: ActivitySleepGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySleepGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //counter variables for use in creating graph
        var dateCounter = 0f
        var sleepStartCounter = 0f
        var sleepEndCounter = 0f


        var sleepList = ArrayList<Sleep>()

        //getting user information
        val user = Firebase.auth.currentUser

        val userId = user?.uid

        //getting database information and updating moodList array
        val db = FirebaseFirestore.getInstance().collection("sleep")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val sleep = document.toObject(Sleep::class.java)
                    sleepList.add(sleep)
                    Log.d("Retrieval Success", "Got data correctly.")
                }

                Log.d("Current page position", "Before For Loop")
                //counting numbers for each mood
                for(sleep in sleepList) {
                    Log.d("Track your sleep", sleep.date.toString())
                    when {
                        sleep.date.equals("date") -> dateCounter++
                    }
                    Log.d("Track your sleep", sleep.sleepStart.toString())
                    when {
                        sleep.sleepStart.equals("start") -> sleepStartCounter++
                    }
                    Log.d("Track your sleep", sleep.sleepEnd.toString())
                    when {
                        sleep.sleepEnd.equals("end") -> sleepEndCounter++
                    }
                }

                Log.d("Current page position", "After For Loop")

                //generating the graph
                val graph = binding.sleepGraph

                val entries: MutableList<BarEntry> = ArrayList()
                entries.add(BarEntry(0f, dateCounter))
                entries.add(BarEntry(1f, sleepStartCounter))
                entries.add(BarEntry(2f, sleepEndCounter))
                val set = BarDataSet(entries, "Total Sleep Tracked")

                val data = BarData(set)
                data.setBarWidth(0.9f)
                graph.setData(data)
                graph.setFitBars(true)
                graph.setBackgroundColor(WHITE)

                //formatting labels
                val sleepLabels = arrayOf("Date", "Sleep Start", "Sleep End")

                val formatter: ValueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase): String {
                        return sleepLabels.get(value.toInt())
                    }
                }

                val xAxis = graph.getXAxis()
                xAxis.setGranularity(1f)
                xAxis.setValueFormatter(formatter)
                graph.invalidate() //refreshes the graph


            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed retrieving documents.", Toast.LENGTH_LONG).show()
                Log.d("Retrieval Failure", "Could not get data.")
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {

        var intent = Intent(this, MoodsListActivity::class.java)
        startActivity(intent)
        this.finish()

    }
}