package com.example.yeebum.screens.components

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.yeebum.R

class Navigation {

    //get current nav controller
    fun getCurrentNavController(activity: FragmentActivity): NavController =
        activity.findNavController(R.id.my_nav_host_fragment)


    //get current destination
    fun getCurrentDestination(activity: FragmentActivity): Int =
        getCurrentNavController(activity).currentDestination!!.id

    //get nav options
    fun getNavOptions(deleteBackStack:Boolean): NavOptions {
        val options = NavOptions.Builder()
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_out_left)
            .setEnterAnim(R.anim.slide_in_right)

        if(deleteBackStack) options.setPopUpTo(R.id.yeebum_nav,true)

        return options.build()
    }
}