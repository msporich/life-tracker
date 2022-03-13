package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.lifetracker.databinding.ActivityMoodsListBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/*
* Moods List Activity
*
* Author: Mark Sporich, #200399323
* Description: This activity shows all the user's previous
*              mood entries in the database. It is connected
*              to several classes that aid in this functionality.
* */

class MoodsListActivity : AppCompatActivity(), MoodsListViewAdapter.MoodItemListener {

    private lateinit var binding: ActivityMoodsListBinding
    private lateinit var viewModel : MoodsListViewModel
    private lateinit var viewModelFactory : MoodsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoodsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = Firebase.auth.currentUser

        val userId = user?.uid

        userId?.let {

            viewModelFactory = MoodsViewModelFactory(userId)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MoodsListViewModel::class.java)
            viewModel.getMoods().observe( this, { moods ->

                var recyclerViewAdapter = MoodsListViewAdapter(this, moods, this)
                binding.moodsRecycler.adapter = recyclerViewAdapter
                binding.moodsRecycler.layoutManager = LinearLayoutManager(this)

            })

        }

    }

    override fun moodSelected(mood: Mood) {

        //val intent = Intent(this, thread::class.java)
        //intent.putExtra("topicId", topic.id)
        //intent.putExtra("topicTitle", topic.title)
        //startActivity(intent)

        Toast.makeText(this, "PLACEHOLDER. USER HAS CLICKED ON MOOD.", Toast.LENGTH_LONG).show()

    }

}