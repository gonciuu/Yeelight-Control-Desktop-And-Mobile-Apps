package com.example.yeebum.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bulbs_table")
data class Bulb(var name: String, var ip: String, var port: Int){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}