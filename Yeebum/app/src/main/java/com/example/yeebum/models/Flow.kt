package com.example.yeebum.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "flows_table")
data class Flow(@PrimaryKey(autoGenerate = true) val id:Int, val name:String, val duration:Int, val actions:String)