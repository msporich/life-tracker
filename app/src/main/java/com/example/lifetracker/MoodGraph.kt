package com.example.lifetracker

import android.content.Intent
import android.graphics.Color.WHITE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lifetracker.databinding.ActivityMoodGraphBinding
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





class MoodGraph : AppCompatActivity() {

    private lateinit var binding: ActivityMoodGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoodGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //counter variables for use in creating graph
        var happyCounter = 0f
        var sadCounter = 0f
        var angryCounter = 0f
        var neutralCounter = 0f

        var moodList = ArrayList<Mood>()

        //getting user information
        val user = Firebase.auth.currentUser

        val userId = user?.uid

        //getting database information and updating moodList array
        val db = FirebaseFirestore.getInstance().collection("moods")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val mood = document.toObject(Mood::class.java)
                    moodList.add(mood)
                    Log.d("Retrieval Success", "Got data correctly.")
                }

                Log.d("Current page position", "Before For Loop")
                //counting numbers for each mood
                for(mood in moodList) {
                    Log.d("Current Mood?", mood.moodType.toString())
                    when {
                        mood.moodType.equals("happy") -> happyCounter++
                        mood.moodType.equals("sad") -> sadCounter++
                        mood.moodType.equals("angry") -> angryCounter++
                        mood.moodType.equals("neutral") -> neutralCounter++
                    }
                }

                Log.d("Current page position", "After For Loop")

                //generating the graph
                val graph = binding.moodGraph

                val entries: MutableList<BarEntry> = ArrayList()
                entries.add(BarEntry(0f, happyCounter))
                entries.add(BarEntry(1f, sadCounter))
                entries.add(BarEntry(2f, angryCounter))
                entries.add(BarEntry(3f, neutralCounter))
                val set = BarDataSet(entries, "Total Mood Counts")

                val data = BarData(set)
                data.setBarWidth(0.9f)
                graph.setData(data)
                graph.setFitBars(true)
                graph.setBackgroundColor(WHITE)

                //formatting labels
                val moodLabels = arrayOf("Happy", "Sad", "Angry", "Neutral")

                val formatter: ValueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase): String {
                        return moodLabels.get(value.toInt())
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