package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lifetracker.databinding.ActivityFitnessBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class FitnessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFitnessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFitnessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.floatingActionButton.setOnClickListener {
            var intent = Intent(this, FitnessHomePageActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSubmit.setOnClickListener {
            if (binding.txtExerciseName.text.toString().isNotEmpty() && binding.txtEquipment.text.toString().isNotEmpty()
                && binding.spinnerWeight.selectedItemPosition > 0 && binding.spinnerReps.selectedItemPosition > 0
                && binding.spinnerSets.selectedItemPosition > 0) {
                val workout = Workout()
                workout.exerciseName = binding.txtExerciseName.text.toString()
                workout.equipment = binding.txtEquipment.text.toString()
                workout.weight = binding.spinnerWeight.selectedItem.toString().toInt()
                workout.reps = binding.spinnerReps.selectedItem.toString().toInt()
                workout.sets = binding.spinnerSets.selectedItem.toString().toInt()

                val user = Firebase.auth.currentUser
                workout.userId = user?.uid

                val dateTimeInstance = Calendar.getInstance()
                val format = SimpleDateFormat("yyyy/MM/dd")
                val dateTime = format.format(dateTimeInstance.time).toString()

                workout.dateCreated = dateTime

                val db = FirebaseFirestore.getInstance().collection("workouts")
                workout.workoutId = db.document().id

                db.document(workout.workoutId!!).set(workout)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Exercise successfully added!", Toast.LENGTH_LONG).show()
                        binding.txtExerciseName.setText("")
                        binding.txtEquipment.setText("")
                        binding.spinnerWeight.setSelection(0)
                        binding.spinnerReps.setSelection(0)
                        binding.spinnerSets.setSelection(0)
                        startActivity(Intent(this, GridRecyclerActivity::class.java))
                    }
                    .addOnFailureListener {exception ->
                        Toast.makeText(this, "Failed to add Exercise.", Toast.LENGTH_LONG).show()
                        var message = exception.localizedMessage
                        message?.let{
                            Log.i("DB Message", message)
                        }
                    }
                }
            else {
                Toast.makeText(this, "Please fill out all the information about your workout.", Toast.LENGTH_LONG).show()
            }
        }
    }
}