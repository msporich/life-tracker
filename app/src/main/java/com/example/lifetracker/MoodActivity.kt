package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lifetracker.databinding.ActivityMoodBinding
import com.example.lifetracker.databinding.ActivitySleepBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

/*

Mood Tracker - Main Activity Page

Author: Mark Sporich, #200399323
Description: This page allows the user to track their current "mood"
             by adding it to the database. It also allows the user to
             move to another activity where they will be able to view
             all moods they have input into the database.

 */

class MoodActivity : AppCompatActivity() {

    private lateinit var saveData: SaveData

    private lateinit var binding: ActivityMoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        saveData = SaveData(this)
        if (saveData.loadDarkModeState() == true) {
            setTheme(R.style.Theme_LifeTrackerNight)
        } else {
            setTheme(R.style.Theme_LifeTracker)
        }
        super.onCreate(savedInstanceState)

        binding = ActivityMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //getting the current user for use in adding entry to the database
        val user = Firebase.auth.currentUser

        binding.addMoodButton.setOnClickListener {

            //checking to see if the user has selected a mood or not
            if (binding.moodGroup.getCheckedRadioButtonId() == -1) {

                Toast.makeText(this, "Please select a current mood.", Toast.LENGTH_LONG).show()

            }
            else {

                //creating new mood
                val mood = Mood()

                //retrieving the user ID so Mood entries are linked to the user
                mood.userId = user?.uid

                //checking to see which radio button has been checked
                //and assigning the corresponding mood to the Mood object

                when {
                    binding.neutralButton.isChecked() -> mood.moodType = "neutral"
                    binding.happyButton.isChecked() -> mood.moodType = "happy"
                    binding.sadButton.isChecked() -> mood.moodType = "sad"
                    binding.angryButton.isChecked() -> mood.moodType = "angry"
                    else -> Toast.makeText(this, "Error: Please select a mood.", Toast.LENGTH_LONG).show()
                }

                //formatting date and time for date of Mood entry
                val dateTimeInstance = Calendar.getInstance()
                val format = SimpleDateFormat("yyyy/MM/dd 'at' HH:mm:ss")
                val dateTime = format.format(dateTimeInstance.time).toString()

                mood.dateCreated = dateTime

                //loading the database
                val dbMoods = FirebaseFirestore.getInstance().collection("moods")
                mood.id = dbMoods.document().id

                //storing the mood object in the database
                dbMoods.document(mood.id!!).set(mood)
                    .addOnSuccessListener {

                        Toast.makeText(this, "New Mood Added", Toast.LENGTH_LONG).show()

                    }
                    .addOnFailureListener {

                        Toast.makeText(this, "Mood creation in database failed.", Toast.LENGTH_LONG).show()

                    }

            }

        }

        binding.viewMoodsButton.setOnClickListener {

            var intent = Intent(this, MoodsListActivity::class.java)
            startActivity(intent)

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {

        var intent = Intent(this, ModulesActivity::class.java)
        startActivity(intent)
        this.finish()

    }

}