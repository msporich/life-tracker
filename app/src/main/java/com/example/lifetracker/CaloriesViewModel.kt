package com.example.lifetracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CaloriesViewModel : ViewModel() {

    // Mutable list to store Food objects
    private val caloriesList = MutableLiveData<List<Food>>()

    // Initialize ViewModel
    init {
        loadCaloriesList()
    }

    private fun loadCaloriesList() {

        val db = FirebaseFirestore.getInstance().collection("food")
            .orderBy("dateConsumed", Query.Direction.DESCENDING)

        db.addSnapshotListener{ documents, e ->
            e?.let {
                Log.w("Database", "Listen failed", e)
                return@addSnapshotListener
            }

            Log.d("Database", "Success: ${documents?.size()} records found")
            val foodList = ArrayList<Food>()

            documents?.let {
                for (document in documents) {
                    try {
                        val food = document.toObject(Food::class.java)
                        foodList.add(food)
                    } catch(e : Exception) {
                        Log.e("Error", "Exception Thrown", e)
                    }
                }
            }
            caloriesList.value = foodList
        }

    }
    fun getCaloriesList() : LiveData<List<Food>> {
        return caloriesList
    }

}