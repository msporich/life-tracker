package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lifetracker.databinding.ActivityMoodEditingBinding
import com.google.firebase.firestore.FirebaseFirestore

class MoodEditing : AppCompatActivity() {

    private lateinit var binding: ActivityMoodEditingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoodEditingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val moodId = intent.getStringExtra("moodId")
        val moodType = intent.getStringExtra("moodType")
        val moodDate = intent.getStringExtra("moodDate")

        binding.moodEditDate.text = moodDate

        when {
            moodType.equals("neutral") -> binding.editNeutral.setChecked(true)
            moodType.equals("happy") -> binding.editHappy.setChecked(true)
            moodType.equals("sad") -> binding.editSad.setChecked(true)
            moodType.equals("angry") -> binding.editAngry.setChecked(true)
        }

        val dbMoods = FirebaseFirestore.getInstance().collection("moods")

        binding.moodUpdateButton.setOnClickListener {

            var updatedMood = ""

            when {
                binding.editNeutral.isChecked() -> updatedMood = "neutral"
                binding.editHappy.isChecked() -> updatedMood = "happy"
                binding.editSad.isChecked() -> updatedMood = "sad"
                binding.editAngry.isChecked() -> updatedMood = "angry"
            }

            dbMoods.document(moodId!!).update("moodType", updatedMood)
                .addOnSuccessListener {
                    Toast.makeText(this, "Mood updated.", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MoodsListActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update mood.", Toast.LENGTH_LONG).show()
                }

        }

        binding.moodDeleteButton.setOnClickListener {

            dbMoods.document(moodId!!).delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Mood deleted.", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MoodsListActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to delete mood.", Toast.LENGTH_LONG).show()
                }

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