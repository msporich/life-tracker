package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProvider
import com.example.lifetracker.databinding.ActivitySleepsListingBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SleepsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepsListingBinding
    private lateinit var viewModel : SleepsListViewModel
    private lateinit var viewModelFactory : SleepsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySleepsListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = Firebase.auth.currentUser

        val userId = user?.uid

        userId?.let {

            viewModelFactory = SleepsViewModelFactory(userId)
            viewModel = ViewModelProvider(this, viewModelFactory).get(SleepsListViewModel::class.java)
            viewModel.getSleeps().observe( this) { sleeps ->

                var recyclerViewAdapter = SleepsListViewAdapter(this, sleeps)
                binding.sleepsRecycler.adapter = recyclerViewAdapter
                binding.sleepsRecycler.layoutManager = LinearLayoutManager(this)
            }
        }

        binding.buttonSleepGraph.setOnClickListener {
            Toast.makeText(this, "Sleep Graph Under Construction", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        var intent = Intent(this, SleepActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}