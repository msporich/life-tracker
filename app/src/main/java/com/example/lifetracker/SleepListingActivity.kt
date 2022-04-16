package com.example.lifetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.lang.Exception

class SleepListingActivity : AppCompatActivity() {
    private val sleep = MutableLiveData<List<Sleep>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun loadSleeps() {
            val db = FirebaseFirestore.getInstance().collection("sleep")
                .orderBy("dateCreated", Query.Direction.ASCENDING)
            db.addSnapshotListener{ documents, exception ->
                exception?.let{
                    Log.i("DB_Response", "Listen Failed : "+ exception)
                    return@addSnapshotListener
                }
                Log.i("DB_Response", "# of elements returned: ${documents?.size()}")
                val sleepList = ArrayList<Sleep>()

                documents?.let {
                    for (document in documents) {
                        try {
                            val sleep = document.toObject(Sleep::class.java)
                            sleepList.add(sleep)
                        } catch(e : Exception)
                        {
                            Log.i("DB_Response", document.toString())
                        }
                    }
                }
                sleep.value = sleepList
            }
        }

        fun getSleeps() : LiveData<List<Sleep>> {
            return sleep
        }
    }
    }