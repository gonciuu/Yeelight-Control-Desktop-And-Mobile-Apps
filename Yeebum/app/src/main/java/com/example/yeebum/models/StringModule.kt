package com.example.yeebum.models

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class StringModule {
    @Provides
    fun provideString():String = ""
}