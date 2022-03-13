package com.example.lifetracker

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
* Moods List View Adapter
*
* Author: Mark Sporich, #200399323
* Description: This class helps assign different entries from the moods list
*              to elements of the item_mood xml file.
* */

class MoodsListViewAdapter (val context: Context, val moods: List<Mood>, val itemListener: MoodItemListener): RecyclerView.Adapter<MoodsListViewAdapter.MoodViewHolder>() {

    inner class MoodViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val moodItem = itemView.findViewById<LinearLayout>(R.id.moodItem)
        val moodType = itemView.findViewById<TextView>(R.id.moodType)
        val moodDate = itemView.findViewById<TextView>(R.id.moodDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_mood, parent, false)
        return MoodViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MoodViewHolder, position: Int) {
        val mood = moods[position]
        with (viewHolder) {
            when {
                mood.moodType.equals("neutral") -> moodItem.setBackgroundResource(R.drawable.yellow_rounded_background)
                mood.moodType.equals("happy") -> moodItem.setBackgroundResource(R.drawable.green_rounded_background)
                mood.moodType.equals("sad") -> moodItem.setBackgroundResource(R.drawable.blue_rounded_background)
                mood.moodType.equals("angry") -> moodItem.setBackgroundResource(R.drawable.red_rounded_background)
            }
            moodType.text = mood.moodType
            moodDate.text = mood.dateCreated
            itemView.setOnClickListener {
                itemListener.moodSelected(mood)
            }
        }
    }

    override fun getItemCount(): Int {
        return moods.size
    }

    interface MoodItemListener {
        fun moodSelected( mood : Mood)
    }

}