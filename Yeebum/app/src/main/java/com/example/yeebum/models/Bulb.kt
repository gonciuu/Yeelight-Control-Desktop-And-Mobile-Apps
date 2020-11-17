package com.example.yeebum.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bulbs_table")
data class Bulb(@PrimaryKey(autoGenerate = true) val id:Int, val name: String, val ip: String, val port: Int)