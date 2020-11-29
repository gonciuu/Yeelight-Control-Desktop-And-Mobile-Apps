package com.example.yeebum.models

import javax.inject.Inject

data class Action @Inject constructor(
    val type: ActionType,
    var color: Int,
    var brightness: Int,
    var duration: Int
)