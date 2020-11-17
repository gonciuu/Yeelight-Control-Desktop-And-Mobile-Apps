package com.example.yeebum.databases.bulbs_database

import androidx.annotation.WorkerThread
import com.example.yeebum.models.Bulb
import kotlinx.coroutines.flow.Flow

class BulbsRepository(private val bulbsDao: BulbsDao) {

    val allBulbs: Flow<List<Bulb>> = bulbsDao.getAllBulbs()


    @WorkerThread
    suspend fun insertBulb(bulb: Bulb){
        bulbsDao.insertBulb(bulb)
    }

    @WorkerThread
    suspend fun deleteBulb(bulb: Bulb){
        bulbsDao.deleteBulb(bulb)
    }

    @WorkerThread
    suspend fun deleteAllBulbs(){
        bulbsDao.deleteAllBulbs()
    }


}