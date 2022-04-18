package com.example.lifetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SleepsViewModelFactory (private val userId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepsListViewModel::class.java))
            return SleepsListViewModel(userId) as T
        else
            throw IllegalArgumentException("Unknown View Model")
    }
}