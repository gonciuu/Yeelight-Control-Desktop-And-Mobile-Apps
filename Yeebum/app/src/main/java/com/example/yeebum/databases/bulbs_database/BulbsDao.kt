package com.example.yeebum.databases.bulbs_database

import androidx.compose.runtime.onActive
import androidx.room.*
import com.example.yeebum.models.Bulb
import kotlinx.coroutines.flow.Flow

@Dao
interface BulbsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBulb(bulb: Bulb)

    @Delete
    suspend fun deleteBulb(bulb: Bulb)

    @Update
    suspend fun updateBulb(bulb: Bulb)

    @Query("SELECT * FROM bulbs_table")
    fun getAllBulbs() : Flow<List<Bulb>>

    @Query("DELETE FROM bulbs_table")
    suspend fun deleteAllBulbs()

}