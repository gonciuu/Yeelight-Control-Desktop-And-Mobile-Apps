package com.example.yeebum.databases.bulbs_database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BulbsViewModelFactory(private val repository: BulbsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BulbsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BulbsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

