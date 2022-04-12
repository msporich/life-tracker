package com.example.lifetracker

import android.content.Context
import android.content.SharedPreferences

class SaveData (context: Context) {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("filename", Context.MODE_PRIVATE)

    fun setDarkModeState(state: Boolean?) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("Night Mode", state!!)
        editor.apply()
    }

    fun loadDarkModeState(): Boolean? {
        return sharedPreferences.getBoolean("Night Mode", false)
    }
}