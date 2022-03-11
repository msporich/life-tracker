package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.activity.viewModels
import com.example.lifetracker.databinding.ActivityMoodsListBinding

class MoodsListActivity : AppCompatActivity(), MoodsListViewAdapter.MoodItemListener {

    private lateinit var binding: ActivityMoodsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoodsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel : MoodsListViewModel by viewModels()

        viewModel.getMoods().observe( this, { moods ->

            var recyclerViewAdapter = MoodsListViewAdapter(this, moods, this)
            binding.moodsRecycler.adapter = recyclerViewAdapter
            binding.moodsRecycler.layoutManager = LinearLayoutManager(this)

        })
    }

    override fun moodSelected(mood: Mood) {

        //val intent = Intent(this, thread::class.java)
        //intent.putExtra("topicId", topic.id)
        //intent.putExtra("topicTitle", topic.title)
        //startActivity(intent)

        Toast.makeText(this, "PLACEHOLDER. USER HAS CLICKED ON MOOD.", Toast.LENGTH_LONG).show()

    }

}