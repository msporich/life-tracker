package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifetracker.databinding.ActivityProfileSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileSettingsActivity : AppCompatActivity() {


    private lateinit var saveData: SaveData
    private lateinit var binding: ActivityProfileSettingsBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        saveData = SaveData(this)
        if (saveData.loadDarkModeState() == true) {
            setTheme(R.style.Theme_LifeTrackerNight)
        } else {
            setTheme(R.style.Theme_LifeTracker)
        }

        super.onCreate(savedInstanceState)
        binding = ActivityProfileSettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        this.setTitle("Profile Settings")

        val user = Firebase.auth.currentUser

        if(user == null){
            logout()
        }
        else {
            user?.let {
                // Name, email address, and profile photo Url
                val name = user.displayName
                val email = user.email

                binding.userName.text = name
                binding.userEmailID.text = email

            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun logout()
    {
        auth.signOut()
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}