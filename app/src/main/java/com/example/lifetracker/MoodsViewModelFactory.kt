package com.example.lifetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
* Moods View Model Factory
*
* Author: Mark Sporich, #200399323
* Description: This class ensures that the view model can survive
*              changes to the screen such as rotations, and ensures
*              that proper filtering can be done on the database.
* */

class MoodsViewModelFactory (private val userId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoodsListViewModel::class.java))
            return MoodsListViewModel(userId) as T
        else
            throw IllegalArgumentException("Unknown View Model")
    }

}
