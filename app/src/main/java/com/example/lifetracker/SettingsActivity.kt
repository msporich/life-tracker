package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lifetracker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var saveData: SaveData
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        saveData = SaveData(this)
        if (saveData.loadDarkModeState() == true) {
            setTheme(R.style.Theme_LifeTrackerNight)
        } else {
            setTheme(R.style.Theme_LifeTracker)
        }
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.setTitle("Settings")



        binding.buttonProfileSettings.setOnClickListener {
            var intent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(intent)
        }


        binding.buttonAppAppearance.setOnClickListener {
            var intent = Intent(this, ThemeActivity::class.java)
            startActivity(intent)
        }

        binding.buttonFriendSettings.setOnClickListener {
            Toast.makeText(this, "Page Making in Progress", Toast.LENGTH_LONG).show()
        }

        binding.buttonMeasurementSettings.setOnClickListener {
            Toast.makeText(this, "Page Making in Progress", Toast.LENGTH_LONG).show()
        }

        binding.buttonAccessibilitySettings.setOnClickListener {
            Toast.makeText(this, "Page Making in Progress", Toast.LENGTH_LONG).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onRestart() {
        refreshPage()
        super.onRestart()
    }

    private fun refreshPage(){
        var intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }
}