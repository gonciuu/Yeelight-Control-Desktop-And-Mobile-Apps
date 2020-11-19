package com.example.yeebum

import android.app.Application
import com.example.yeebum.databases.bulbs_database.BulbsDatabase
import com.example.yeebum.databases.bulbs_database.BulbsRepository
import com.example.yeebum.databases.flows_database.FlowsDatabase
import com.example.yeebum.databases.flows_database.FlowsRepository

class YeebumApplication : Application() {
     //get bulbs repository
     private val bulbsDatabase by lazy { BulbsDatabase.getInstance(this)}
     val bulbsRepository by lazy { BulbsRepository(bulbsDatabase!!.bulbsDao()) }

     //get flows repository
     private val flowsDatabase by lazy { FlowsDatabase.getInstance(this) }
     val flowsRepository by lazy { FlowsRepository(flowsDatabase!!.flowsDao())}
}