package com.example.yeebum.screens.adapters.recycler_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.models.Flow
import com.example.yeebum.screens.FlowsInterface
import com.example.yeebum.screens.adapters.recycler_views.view_holders.FlowsViewHolder

class FlowsRecyclerViewAdapter(private val listener: FlowsInterface, private val listOfFlows:List<Flow>):RecyclerView.Adapter<FlowsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowsViewHolder =
         FlowsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.flow_card,parent,false))


    override fun onBindViewHolder(holder: FlowsViewHolder, position: Int) {
        val flow = listOfFlows[holder.adapterPosition]
        holder.allFlowCard.setOnClickListener {
            listener.onSelectAction()
        }
        holder.flowName.text = flow.name
        holder.flowDuration.text = flow.duration.toString()
    }

    override fun getItemCount(): Int = listOfFlows.size

}