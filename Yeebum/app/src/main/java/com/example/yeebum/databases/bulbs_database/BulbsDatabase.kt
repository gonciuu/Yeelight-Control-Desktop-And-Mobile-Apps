package com.example.yeebum.databases.bulbs_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.yeebum.models.Bulb

@Database(entities = [Bulb::class], version = 1, exportSchema = false)
abstract class BulbsDatabase : RoomDatabase() {

    abstract fun bulbsDao(): BulbsDao

    companion object {
        @Volatile
        private var instance: BulbsDatabase? = null
        fun getInstance(context: Context): BulbsDatabase? {
            if (instance == null) instance = synchronized(this) {
                Room.databaseBuilder(context, BulbsDatabase::class.java, "bulbs_table").build()
            }
            return instance
        }
    }
}

