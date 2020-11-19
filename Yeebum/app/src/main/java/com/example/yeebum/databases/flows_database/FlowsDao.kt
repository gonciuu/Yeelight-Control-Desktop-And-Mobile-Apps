package com.example.yeebum.databases.flows_database

import androidx.room.*
import com.example.yeebum.models.Flow

@Dao
interface FlowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlow(flow: Flow)

    @Delete
    suspend fun deleteFlow(flow: Flow)

    @Query("DELETE FROM flows_table")
    suspend fun deleteAllFlows()

    @Query("SELECT * FROM flows_table")
    fun getAllFlows(): kotlinx.coroutines.flow.Flow<List<Flow>>

}