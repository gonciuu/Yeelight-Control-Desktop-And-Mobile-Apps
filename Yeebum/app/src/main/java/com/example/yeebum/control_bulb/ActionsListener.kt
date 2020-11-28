package com.example.yeebum.control_bulb

import com.example.yeebum.models.Action

interface ActionsListener {

    fun onActionEdit(action:Action)
    fun onActionDelete(action: Action)
    fun onActionSelected(action: Action)

}