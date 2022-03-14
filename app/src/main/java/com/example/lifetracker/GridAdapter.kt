package com.example.lifetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GridAdapter(val context : Context,
                  val workouts : List<Workout>, val itemListener: WorkoutItemListener) : RecyclerView.Adapter<GridAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textViewExercise = itemView.findViewById<TextView>(R.id.textViewExercise)
        val textViewDate = itemView.findViewById<TextView>(R.id.textViewDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_grid_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        viewHolder.textViewExercise.text = workout.exerciseName
        viewHolder.textViewDate.text = workout.dateCreated

        viewHolder.itemView.setOnClickListener {
            itemListener.workoutSelected(workout)
        }
    }

    override fun getItemCount(): Int {
        return workouts.size
    }

    interface WorkoutItemListener {
        fun workoutSelected( workout : Workout)
    }
}