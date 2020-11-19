package com.example.yeebum.screens.adapters.recycler_views.view_holders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.flow_card.view.*

class FlowsViewHolder(v:View) :RecyclerView.ViewHolder(v) {
    val allFlowCard: LinearLayout = v.allFlowCard
    val flowName: TextView = v.flowName
    val flowDuration: TextView = v.flowDuration
}