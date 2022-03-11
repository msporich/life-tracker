package com.example.lifetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoodsListViewAdapter (val context: Context, val moods: List<Mood>, val itemListener: MoodItemListener): RecyclerView.Adapter<MoodsListViewAdapter.MoodViewHolder>() {

    inner class MoodViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
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