package com.example.yeebum.databases.flows_database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class FlowsViewModelFactory(private val repository: FlowsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FlowsViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            return FlowsViewModel(repository) as T
        throw IllegalStateException("Unknown ViewModel class")
    }
}