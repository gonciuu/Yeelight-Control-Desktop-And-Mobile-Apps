package com.example.yeebum.models

import com.example.yeebum.screens.ActionDetailsFragment
import dagger.Component
import javax.inject.Singleton

@Component
interface FlowComponent {

    fun inject(fragment: ActionDetailsFragment)
}