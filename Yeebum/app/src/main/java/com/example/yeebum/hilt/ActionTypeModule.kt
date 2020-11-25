package com.example.yeebum.hilt

import com.example.yeebum.models.ActionType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActionTypeModule {
    @Provides
    fun provideActionType():ActionType = ActionType.Color
}