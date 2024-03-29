package com.example.yeebum.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject


@Entity(tableName = "flows_table")
data class Flow @Inject constructor(var name:String, val actions:ArrayList<Action>){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

