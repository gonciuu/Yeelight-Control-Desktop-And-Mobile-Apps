package com.example.yeebum.screens.flows_control

import com.example.yeebum.models.Flow

interface FlowsInterface {
    fun onEditFlow(flow: Flow)

    fun onStartFlow(flow: Flow)
}