package com.example.yeebum.screens.components

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.yeebum.MainActivity
import com.example.yeebum.R
import kotlinx.android.synthetic.main.drawer.view.*

class AppDrawer {

    //------------------------| Open drawer on whatever button click |------------------------
    fun setOpenDrawer(handler: View, activity: FragmentActivity) {
        handler.setOnClickListener {
            val drawer = activity.findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.openDrawer(GravityCompat.START)
        }
    }
    //=========================================================================================


    //----------------------------| Setup drawer with navigation |-------------------------------

    fun setupDrawerNavigation(activity: MainActivity) {
        val navigation = Navigation()
        val drawer = activity.findViewById<LinearLayout>(R.id.myDrawer)
        val listOfBoxes = arrayListOf<LinearLayout>(
            drawer.homeBox,
            drawer.addBulbBox,
            drawer.flowsBox,
            drawer.settingsBox,
            drawer.helpCenterBox,
            drawer.bugReportBox
        )
        val listOfDestinations = arrayListOf<Int>(
            R.id.allBulbsFragment,
            R.id.enterBulbDataFragment,
            R.id.flowControlFragment,
            R.id.flowControlFragment,
            R.id.allBulbsFragment,
            R.id.bugReportFragment
        )
        listOfBoxes.forEach {
            it.setOnClickListener { _ ->
                val currentDestination = navigation.getCurrentDestination(activity)
                val destination = listOfDestinations[listOfBoxes.indexOf(it)]
                activity.findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (currentDestination != destination) navigation.getCurrentNavController(activity).navigate(
                        destination,
                        null,
                        navigation.getNavOptions(currentDestination == R.id.controlFragment)
                    )
                }, 250) //await to drawer close
            }
        }
    }
    //===========================================================================================



}