package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lifetracker.databinding.ActivityMoodGraphBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

class MoodGraph : AppCompatActivity() {

    private lateinit var binding: ActivityMoodGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoodGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //counter variables for use in creating graph
        var happyCounter: Double = 0.0
        var sadCounter: Double = 0.0
        var angryCounter: Double = 0.0
        var neutralCounter: Double = 0.0

        val moodList = ArrayList<Mood>()

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
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed retrieving documents.", Toast.LENGTH_LONG).show()
            }

        //counting numbers for each mood
        for(mood in moodList) {
            when {
                mood.moodType.equals("happy") -> happyCounter++
                mood.moodType.equals("sad") -> sadCounter++
                mood.moodType.equals("angry") -> angryCounter++
                mood.moodType.equals("neutral") -> neutralCounter++
            }
        }

        //generating graph
        val graph = binding.moodGraph
        val series = BarGraphSeries(
            arrayOf<DataPoint>(
                DataPoint(0.0, 3.0),
                DataPoint(1.0, 4.0),
                DataPoint(2.0, 2.0),
                DataPoint(3.0, 6.0),
            )
        )
        graph.addSeries(series)
        graph.getViewport().setScalable(true)

        //series.setSpacing(50)
        series.setDrawValuesOnTop(true)
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