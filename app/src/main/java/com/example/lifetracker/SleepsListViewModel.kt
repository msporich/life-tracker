package com.example.lifetracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class SleepsListViewModel(userId: String) : ViewModel() {

    private val sleeps = MutableLiveData<List<Sleep>>()

    init {
        loadSleeps(userId)
    }

    private fun loadSleeps(userId: String) {
        val db = FirebaseFirestore.getInstance().collection("sleeps")
            .whereEqualTo("userId", userId)
            .orderBy("sleepDate", Query.Direction.DESCENDING)

        db.addSnapshotListener{documents, exception ->
            exception?.let {
                Log.i("DB_Response", "Listen Failed")
                return@addSnapshotListener
            }

            val sleepList = ArrayList<Sleep>()

            documents?.let{
                for (document in documents)
                {
                    try {
                        val sleep = document.toObject(Sleep::class.java)
                        sleepList.add(sleep)
                    }catch(e : Exception)
                    {
                        Log.i("DB_Response", document.toString())
                    }
                }
            }
            sleeps.value = sleepList
        }
    }

    fun getSleeps() : LiveData<List<Sleep>>
    {
        return sleeps
    }
}