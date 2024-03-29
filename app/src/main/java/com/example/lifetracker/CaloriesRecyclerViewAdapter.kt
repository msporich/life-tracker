package com.example.lifetracker

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class CaloriesRecyclerViewAdapter (val context : Context,
                                    val foodList : List<Food>,
                                    val itemListener : FoodItemListener) : RecyclerView.Adapter<CaloriesRecyclerViewAdapter.CaloriesViewHolder>() {

    inner class CaloriesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textViewFoodName = itemView.findViewById<TextView>(R.id.textViewFoodName)
        val textViewCaloriesAmount = itemView.findViewById<TextView>(R.id.textViewCaloriesAmount)
        val textViewDateConsumed = itemView.findViewById<TextView>(R.id.textViewDateConsumed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaloriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_food, parent, false)
        return CaloriesViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: CaloriesViewHolder, position: Int) {
        val food = foodList[position]
        viewHolder.textViewFoodName.text = food.foodName
        viewHolder.textViewCaloriesAmount.text = food.foodCalories.toString() + " cal"
        val dateConsumed = food.dateConsumed.toString()
        val dateConsumedSbstr1 = dateConsumed.subSequence(0, 10).toString()
        val dateConsumedSbstr2 = dateConsumed.subSequence(24, 28).toString()
        val dateConsumedFormatted = "$dateConsumedSbstr1, $dateConsumedSbstr2"
        viewHolder.textViewDateConsumed.text = dateConsumedFormatted

        viewHolder.itemView.setOnClickListener {
            itemListener.foodSelected(food)
        }

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    interface FoodItemListener {
        fun foodSelected(food : Food)
    }

}