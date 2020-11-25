package com.example.yeebum.databases.flows_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.yeebum.models.Flow

@Database(entities = [Flow::class], version = 5, exportSchema = false)
@TypeConverters(ActionsTypeConverter::class)
abstract class FlowsDatabase : RoomDatabase() {

    abstract fun flowsDao(): FlowsDao

    companion object {
        @Volatile
        private var instance: FlowsDatabase? = null
        fun getInstance(context: Context): FlowsDatabase? {
            if (instance == null)
                instance = synchronized(this){
                    Room.databaseBuilder(context, FlowsDatabase::class.java, "flows_table").fallbackToDestructiveMigration().build()
                }
            return instance
        }
    }


}