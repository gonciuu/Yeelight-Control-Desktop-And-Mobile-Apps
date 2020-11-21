package com.example.yeebum.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bulbs_table")
data class Bulb(val name: String, val ip: String, val port: Int){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}