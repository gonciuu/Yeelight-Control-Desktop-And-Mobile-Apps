package com.example.yeebum

import android.app.Application
import com.example.yeebum.databases.bulbs_database.BulbsDatabase
import com.example.yeebum.databases.bulbs_database.BulbsRepository

class YeebumApplication : Application() {
     //get bulbs repository
     private val bulbsDatabase by lazy { BulbsDatabase.getInstance(this)}
     val bulbsRepository by lazy { BulbsRepository(bulbsDatabase!!.bulbsDao()) }
}