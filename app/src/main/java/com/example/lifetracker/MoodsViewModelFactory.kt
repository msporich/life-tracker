package com.example.lifetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MoodsViewModelFactory (private val userId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoodsListViewModel::class.java))
            return MoodsListViewModel(userId) as T
        else
            throw IllegalArgumentException("Unknown View Model")
    }

}
