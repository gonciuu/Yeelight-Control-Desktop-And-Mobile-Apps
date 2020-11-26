package com.example.yeebum.models

import javax.inject.Inject

data class Action @Inject constructor(
    val type: ActionType,
    val color: Int,
    val brightness: Int,
    val duration: Int
)