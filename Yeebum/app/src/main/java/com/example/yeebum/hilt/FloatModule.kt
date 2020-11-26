package com.example.yeebum.hilt

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