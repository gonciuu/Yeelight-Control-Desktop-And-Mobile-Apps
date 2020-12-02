package com.example.yeebum.screens.adapters.recycler_views.view_holders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.device_card.view.*

class DevicesViewHolder(v:View):RecyclerView.ViewHolder(v) {
    val bulbName: TextView = v.bulbName
    val bulbData: TextView = v.bulbData
    val allDeviceCard: LinearLayout = v.allDeviceCard
}