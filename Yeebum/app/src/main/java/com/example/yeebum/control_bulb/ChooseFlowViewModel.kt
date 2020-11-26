package com.example.yeebum.control_bulb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yeebum.models.Flow

class ChooseFlowViewModel:ViewModel() {
    private val flow = MutableLiveData<Flow>()
    fun setFlow(flowToSet:Flow){this.flow.value = flowToSet}
    fun getFlow():LiveData<Flow> = flow
}