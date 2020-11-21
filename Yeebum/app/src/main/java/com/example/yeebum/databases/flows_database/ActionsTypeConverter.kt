package com.example.yeebum.databases.flows_database

import androidx.room.TypeConverter
import com.example.yeebum.models.Action
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ActionsTypeConverter {

    @TypeConverter
    fun fromActionsList(actions:ArrayList<Action>): String{
        val gson: Gson = Gson()
        val type = object: TypeToken<ArrayList<Action>>(){}.type
        return gson.toJson(actions, type)
    }

    @TypeConverter
    fun toActionsList(actionsString: String) : ArrayList<Action>{
        val gson: Gson = Gson()
        val type = object: TypeToken<ArrayList<Action>>(){}.type
        return gson.fromJson(actionsString, type)
    }

}