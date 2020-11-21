package com.example.yeebum.hilt

import com.example.yeebum.screens.flows_control.ActionDetailsFragment
import dagger.Component


@Component(modules = [IntModule::class, FloatModule::class, StringModule::class, ArrayListModule::class])
interface FlowComponent {
    fun inject(fragment: ActionDetailsFragment)
}