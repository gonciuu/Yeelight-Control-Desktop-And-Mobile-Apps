package com.example.yeebum.databases.flows_database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.yeebum.models.Flow
import kotlinx.coroutines.launch

class FlowsViewModel(private val repository: FlowsRepository) : ViewModel() {

    val allFlows: LiveData<List<Flow>> = repository.allFlows.asLiveData()

    fun insertFlow(flow: Flow) = viewModelScope.launch {
        repository.insertFlow(flow)
    }

    fun deleteFlow(flow: Flow) = viewModelScope.launch {
        repository.deleteFlow(flow)
    }

    fun updateFlow(flow: Flow) = viewModelScope.launch {
        repository.updateFlow(flow)
    }


    fun deleteAllFlows() = viewModelScope.launch {
        repository.deleteAllFlows()
    }


}