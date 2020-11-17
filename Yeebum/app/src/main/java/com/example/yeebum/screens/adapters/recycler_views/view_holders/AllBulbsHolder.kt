package com.example.yeebum.screens.adapters.recycler_views.view_holders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bulb_card.view.*

class AllBulbsHolder(v: View) : RecyclerView.ViewHolder(v) {
    val allBulbCard: LinearLayout? = v.allBulbCard
    val bulbName: TextView? = v.bulbName
    val bulbPort: TextView? = v.bulbPort
    val bulbIp: TextView? = v.bulbIp
}