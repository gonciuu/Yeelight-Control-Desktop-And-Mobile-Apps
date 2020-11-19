package com.example.yeebum.screens

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
            R.id.enterBulbDataFragment
        )
        listOfBoxes.forEach {
            it.setOnClickListener { _ ->
                val navController = activity.findNavController(R.id.my_nav_host_fragment)
                val destination = listOfDestinations[listOfBoxes.indexOf(it)]
                activity.findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawer(GravityCompat.START)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (navController.currentDestination?.id != destination) navController.navigate(
                        destination,
                        null,
                        getNavOptions(navController.currentDestination!!.id == R.id.controlFragment)
                    )
                }, 250) //await to drawer close
            }
        }
    }
    //===========================================================================================

    //get nav options
    private fun getNavOptions(deleteBackStack:Boolean): NavOptions {
        val options = NavOptions.Builder()
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_out_left)
            .setEnterAnim(R.anim.slide_in_right)

        if(deleteBackStack) options.setPopUpTo(R.id.yeebum_nav,false)

        return options.build()
    }

}