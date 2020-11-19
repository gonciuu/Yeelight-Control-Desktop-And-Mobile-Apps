package com.example.yeebum.databases.flows_database

import androidx.annotation.WorkerThread
import com.example.yeebum.models.Flow

class FlowsRepository(private val flowsDao: FlowsDao) {

    val allFlows : kotlinx.coroutines.flow.Flow<List<Flow>> = flowsDao.getAllFlows()

    @WorkerThread
    suspend fun insertFlow(flow: Flow){
        flowsDao.insertFlow(flow)
    }

    @WorkerThread
    suspend fun deleteFlow(flow: Flow){
        flowsDao.deleteFlow(flow)
    }

    @WorkerThread
    suspend fun deleteAllFlows(){
        flowsDao.deleteAllFlows()
    }


}