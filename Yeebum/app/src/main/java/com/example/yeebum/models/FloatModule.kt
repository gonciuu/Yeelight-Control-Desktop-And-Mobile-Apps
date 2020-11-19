package com.example.yeebum.models

import com.example.yeebum.screens.flows_control.ActionDetailsFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class FloatModule {
    @Provides
    fun provideFloat():Float = 0.0f
}