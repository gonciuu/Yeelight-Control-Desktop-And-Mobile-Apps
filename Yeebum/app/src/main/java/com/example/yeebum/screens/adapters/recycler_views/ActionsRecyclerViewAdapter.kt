package com.example.yeebum.screens.adapters.recycler_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.screens.adapters.recycler_views.view_holders.ActionsViewHolder

class ActionsRecyclerViewAdapter:RecyclerView.Adapter<ActionsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionsViewHolder {
        return ActionsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.action_card,parent,false))
    }

    override fun onBindViewHolder(holder: ActionsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
       return 2
    }
}