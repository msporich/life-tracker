package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifetracker.databinding.ActivitySleepBinding

class SleepActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySleepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.backFAB.setOnClickListener {
            startActivity(Intent(this, ModulesActivity::class.java))
        }
    }
}