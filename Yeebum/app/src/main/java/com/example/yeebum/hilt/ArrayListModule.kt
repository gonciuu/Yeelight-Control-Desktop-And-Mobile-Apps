package com.example.yeebum.hilt

import com.example.yeebum.models.Action
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ArrayListModule {
    @Provides
    fun provideArrayListOfActions(): ArrayList<Action> = arrayListOf<Action>()
}