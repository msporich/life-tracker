package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        var happyCounter = 0
        var sadCounter = 0
        var angryCounter = 0
        var neutralCounter = 0

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
                val graph = binding.moodGraph

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