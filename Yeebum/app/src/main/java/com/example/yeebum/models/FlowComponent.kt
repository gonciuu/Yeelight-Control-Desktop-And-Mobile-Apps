package com.example.yeebum.models

import com.example.yeebum.screens.flows_control.ActionDetailsFragment
import dagger.Component

@Component
interface FlowComponent {

    fun inject(fragment: ActionDetailsFragment)
}