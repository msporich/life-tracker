package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lifetracker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.setTitle("Settings")

        binding.userSettingsImage.bringToFront()


        binding.buttonProfileSettings.setOnClickListener {
            var intent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(intent)
        }


        binding.buttonAppAppearance.setOnClickListener {
            Toast.makeText(this, "Page Making in Progress", Toast.LENGTH_LONG).show()
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
}