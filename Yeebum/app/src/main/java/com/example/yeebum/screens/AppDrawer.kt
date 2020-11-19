package com.example.yeebum.screens

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import com.example.yeebum.R

class AppDrawer {

    @SuppressLint("WrongConstant")
    fun setOpenDrawer(handler:View, activity:FragmentActivity){
        handler.setOnClickListener {
            val drawer = activity.findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.openDrawer(Gravity.START)
        }
    }

}