package com.example.lifetracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MoodsListViewModel : ViewModel() {

    private val moods = MutableLiveData<List<Mood>>()

    init {
        loadMoods()
    }

    private fun loadMoods() {
        val db = FirebaseFirestore.getInstance().collection("moods")
            .orderBy("dateCreated", Query.Direction.DESCENDING)

        db.addSnapshotListener{documents, exception ->
            exception?.let {
                Log.i("DB_Response", "Listen Failed")
                return@addSnapshotListener
            }

            val moodList = ArrayList<Mood>()

            documents?.let{
                for (document in documents)
                {
                    try {
                        val mood = document.toObject(Mood::class.java)
                        moodList.add(mood)
                    }catch(e : Exception)
                    {
                        Log.i("DB_Response", document.toString())
                    }

                }
            }
            moods.value = moodList

        }

    }

    fun getMoods() : LiveData<List<Mood>>
    {
        return moods
    }

}