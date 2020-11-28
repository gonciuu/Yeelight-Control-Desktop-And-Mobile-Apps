package com.example.yeebum.screens.adapters.recycler_views.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.action_card.view.*

class ActionsViewHolder(v:View):RecyclerView.ViewHolder(v){
    val allCard: ConstraintLayout = v.allActionCard
    val firstValue: TextView = v.firstValue
    val firstValueName: TextView = v.firstValueName
    val secondValue: TextView = v.secondValue
    val secondValueName: TextView = v.secondValueName
    val thirdValueImage: ImageView = v.thirdValueImage
    val thirdValueText: TextView = v.thirdValueText
    val thirdValueName: TextView = v.thirdValueName
}