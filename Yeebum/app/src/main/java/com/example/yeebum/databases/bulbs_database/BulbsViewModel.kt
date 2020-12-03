package com.example.yeebum.databases.bulbs_database

import android.app.Application
import androidx.lifecycle.*
import com.example.yeebum.models.Bulb
import kotlinx.coroutines.launch

class BulbsViewModel(private val repository: BulbsRepository):ViewModel() {

    val allBulbs : LiveData<List<Bulb>> = repository.allBulbs.asLiveData()


    fun insertBulb(bulb: Bulb) = viewModelScope.launch{
        repository.insertBulb(bulb)
    }


    fun deleteBulb(bulb: Bulb) = viewModelScope.launch {
        repository.deleteBulb(bulb)
    }

    fun updateBulb(bulb: Bulb) = viewModelScope.launch {
        repository.updateBulb(bulb)
    }


    fun deleteAllBulbs() = viewModelScope.launch {
        repository.deleteAllBulbs()
    }



}