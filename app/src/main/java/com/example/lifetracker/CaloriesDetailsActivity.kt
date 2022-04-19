package com.example.lifetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.lifetracker.databinding.ActivityCaloriesDetailsBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CaloriesDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCaloriesDetailsBinding
    private lateinit var saveData: SaveData

    override fun onCreate(savedInstanceState: Bundle?) {
        saveData = SaveData(this)
        if (saveData.loadDarkModeState() == true) {
            setTheme(R.style.Theme_LifeTrackerNight)
        } else {
            setTheme(R.style.Theme_LifeTracker)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityCaloriesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val foodId = intent.getStringExtra("foodId")
        val foodName = intent.getStringExtra("foodName")
        val foodCalories = intent.getStringExtra("foodCalories")
        val foodCategory = intent.getStringExtra("foodCategory")
        val dateConsumed = intent.getStringExtra("dateConsumed").toString()
        val dateConsumedSbstr1 = dateConsumed.subSequence(0, 10).toString()
        val dateConsumedSbstr2 = dateConsumed.subSequence(24, 28).toString()
        val dateConsumedFormatted = "$dateConsumedSbstr1, $dateConsumedSbstr2"

        binding.textViewFoodName.text = foodName
        binding.textViewCalories.text = foodCalories
        binding.textViewCategory.text = foodCategory
        binding.textViewDateConsumed.text = dateConsumedFormatted

        // Redirect to edit page
        binding.buttonEdit.setOnClickListener{
            val intent = Intent(this, CaloriesAddNewActivity::class.java)
            intent.putExtra("foodId", foodId)
            intent.putExtra("foodName", foodName)
            intent.putExtra("foodCalories", foodCalories)
            intent.putExtra("foodCategory", foodCategory)
            intent.putExtra("dateConsumed", dateConsumed)
            startActivity(intent)
        }

        // Delete the calorie data if delete button is clicked then redirect to main schedule
        binding.buttonDelete.setOnClickListener{

            val builder = AlertDialog.Builder(this)
            builder
                .setMessage("Delete forever?")
                .setCancelable(true)
                .setPositiveButton("Delete") { dialog, id ->
                    // DELETE
                    val db = FirebaseFirestore.getInstance()
                    db
                        .collection("food")
                        .document(intent.getStringExtra("foodId").toString())
                        .delete()
                        .addOnSuccessListener { Log.i("Database", "Calorie record deleted successfully") }
                        .addOnFailureListener { e -> Log.w("Database", "Error deleting document", e) }
                    Log.i("Database", "Calorie entry deleted")

                    // Redirect to main schedule and display toast
                    val intent = Intent(this, CaloriesRecyclerActivity::class.java)
                    intent.putExtra("toast", "Calorie entry deleted")
                    startActivity(intent)
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    Log.i("Database", "Action was cancelled")
                }
            val alert = builder.create()
            alert.show()
        }

    }
}