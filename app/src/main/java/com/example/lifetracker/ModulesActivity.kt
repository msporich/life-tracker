package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifetracker.databinding.ActivityModulesBinding
import com.firebase.ui.auth.AuthUI

class ModulesActivity : AppCompatActivity() {

    private lateinit var saveData: SaveData
    private lateinit var binding: ActivityModulesBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        saveData = SaveData(this)
        if (saveData.loadDarkModeState() == true) {
            setTheme(R.style.Theme_LifeTrackerNight)
        } else {
            setTheme(R.style.Theme_LifeTracker)
        }

        super.onCreate(savedInstanceState)
        binding = ActivityModulesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalories.setOnClickListener {
            var intent = Intent(this, CaloriesActivity::class.java)
            startActivity(intent)
        }
        binding.buttonFitness.setOnClickListener {
            var intent = Intent(this, FitnessHomePageActivity::class.java)
            startActivity(intent)
        }
        binding.buttonMood.setOnClickListener {
            var intent = Intent(this, MoodActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSleepTracking.setOnClickListener {
            var intent = Intent(this, SleepActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSettings.setOnClickListener {
            var intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogout.setOnClickListener{
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, MainActivity::class.java))
                }
        }
    }

    override fun onRestart() {
        refreshPage()
        super.onRestart()
    }

    private fun refreshPage(){
        var intent = Intent(this, ModulesActivity::class.java)
        startActivity(intent)
    }

}