package com.example.yeebum.screens.components

import android.content.Context
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_settings.*

class Prefs {

    fun getSettings(activity: FragmentActivity): HashMap<String, Boolean> {
        val prefs = activity.getSharedPreferences("OPTIONS", Context.MODE_PRIVATE)
        var data: HashMap<String, Boolean>
        prefs.apply {
            data = hashMapOf(
                "showName" to getBoolean("showName", true),
                "showPort" to getBoolean("showPort", true),
                "showIp" to getBoolean("showIp", true)
            )
        }
        return data
    }

    fun setField(activity: FragmentActivity, fieldName: String, fieldValue: Boolean){
        val prefs = activity.getSharedPreferences("OPTIONS", Context.MODE_PRIVATE)
        prefs.edit().putBoolean(fieldName,fieldValue).apply()
    }


}