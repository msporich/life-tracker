package com.example.lifetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SleepsListViewAdapter (val context: Context, val sleeps: List<Sleep>): RecyclerView.Adapter<SleepsListViewAdapter.SleepViewHolder>() {

    inner class SleepViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val sleepStart = itemView.findViewById<TextView>(R.id.sleepStart)
        val sleepEnd = itemView.findViewById<TextView>(R.id.sleepEnd)
        val sleepDate = itemView.findViewById<TextView>(R.id.sleepDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_sleep, parent, false)
        return SleepViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SleepViewHolder, position: Int) {
        val sleep = sleeps[position]
        with (viewHolder) {
            sleepStart.text = sleep.sleepStart
            sleepEnd.text = sleep.sleepEnd
            sleepDate.text = sleep.sleepDate
//            itemView.setOnClickListener {
//                itemListener.sleepSelected(sleep)
//            }
        }
    }

    override fun getItemCount(): Int {
        return sleeps.size
    }

//    interface SleepItemListener {
//        fun sleepSelected( sleep : Sleep)
//    }
}