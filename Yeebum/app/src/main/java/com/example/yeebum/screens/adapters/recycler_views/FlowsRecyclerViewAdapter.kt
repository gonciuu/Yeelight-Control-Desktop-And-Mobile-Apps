package com.example.yeebum.screens.adapters.recycler_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.screens.FlowsInterface
import com.example.yeebum.screens.adapters.recycler_views.view_holders.FlowsViewHolder

class FlowsRecyclerViewAdapter(private val listener: FlowsInterface):RecyclerView.Adapter<FlowsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowsViewHolder {
        return FlowsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.flow_card,parent,false))
    }

    override fun onBindViewHolder(holder: FlowsViewHolder, position: Int) {
        holder.allFlowCard.setOnClickListener {
            listener.onSelectAction()
        }
    }

    override fun getItemCount(): Int {
       return 20
    }
}