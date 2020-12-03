package com.example.yeebum.screens.bulbs

import com.example.yeebum.models.Bulb

interface BulbsInterface {

    fun onBulbClick(ip:String, port:Int)

    fun onLongClickBulb(bulb: Bulb)
    fun onDeleteBulb(bulb: Bulb)
    fun onEditBulb(bulb: Bulb)

}