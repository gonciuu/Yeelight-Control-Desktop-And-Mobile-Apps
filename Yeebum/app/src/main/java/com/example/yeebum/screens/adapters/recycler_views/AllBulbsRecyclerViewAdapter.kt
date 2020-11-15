package com.example.yeebum.screens.adapters.recycler_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.screens.adapters.recycler_views.view_holders.AllBulbsHolder

class AllBulbsRecyclerViewAdapter(): RecyclerView.Adapter<AllBulbsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBulbsHolder {
       return AllBulbsHolder(LayoutInflater.from(parent.context).inflate(R.layout.bulb_card,parent,false))
    }

    override fun onBindViewHolder(holder: AllBulbsHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 20
    }
}