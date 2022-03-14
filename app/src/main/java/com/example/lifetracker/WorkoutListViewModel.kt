package com.example.lifetracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class WorkoutListViewModel : ViewModel(){
    private val workouts = MutableLiveData<List<Workout>>()
    init {
        loadWorkouts()
    }

    private fun loadWorkouts() {
        val db = FirebaseFirestore.getInstance().collection("workouts")
            .orderBy("dateCreated", Query.Direction.ASCENDING)
        db.addSnapshotListener{ documents, exception ->
            exception?.let {
                Log.i("DB_Response", "Listen Failed : "+ exception)
                return@addSnapshotListener
            }
            Log.i("DB_Response", "# of elements returned: ${documents?.size()}")
            val workoutList = ArrayList<Workout>()

            documents?.let {
                for (document in documents) {
                    try {
                        val workout = document.toObject(Workout::class.java)
                        workoutList.add(workout)
                    } catch(e : Exception)
                    {
                        Log.i("DB_Response", document.toString())
                    }
                }
            }
            workouts.value = workoutList
        }
    }

    fun getWorkouts() : LiveData<List<Workout>> {
        return workouts
    }
}