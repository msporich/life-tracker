package com.example.lifetracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lifetracker.databinding.ActivitySleepBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class SleepActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySleepBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySleepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = Firebase.auth.currentUser

        binding.backFAB.setOnClickListener {
            startActivity(Intent(this, ModulesActivity::class.java))
        }

<<<<<<< Updated upstream
=======
        binding.button4.setOnClickListener {
            startActivity(Intent(this, SleepListActivity::class.java))
        }

        binding.button5.setOnClickListener {
            startActivity(Intent(this, SleepGraph::class.java))
        }

>>>>>>> Stashed changes
        binding.button2.setOnClickListener {
            if (binding.editTextDate.text.toString().isEmpty() &&
                binding.editTextTime.text.toString().isEmpty() &&
                binding.editTextTime2.text.toString().isEmpty()

            ) {
                Toast.makeText(this, "Please fill in all boxes.", Toast.LENGTH_LONG).show()
            }
            else {
                //creating an instance of a sleep time
                val sleep = Sleep()
                sleep.userId = user?.uid

                sleep.date = binding.editTextDate.text.toString()
                sleep.sleepStart = binding.editTextTime.text.toString()
                sleep.sleepEnd = binding.editTextTime2.text.toString()

                val dateTimeInstance = Calendar.getInstance()
                val format = SimpleDateFormat("yyyy/MM/dd 'at' HH:mm:ss")
                val dateTime = format.format(dateTimeInstance.time).toString()

                sleep.date = dateTime

                //loading the database
                val dbSleep = FirebaseFirestore.getInstance().collection("sleep")
                sleep.id = dbSleep.document().id

                //storing the sleep object in the database
                dbSleep.document(sleep.id!!).set(sleep)
                    .addOnSuccessListener {
                        Toast.makeText(this, "New Sleep Time Added", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "New Sleep Time Failed", Toast.LENGTH_LONG).show()


                    }
            }



            }
        }
    }

