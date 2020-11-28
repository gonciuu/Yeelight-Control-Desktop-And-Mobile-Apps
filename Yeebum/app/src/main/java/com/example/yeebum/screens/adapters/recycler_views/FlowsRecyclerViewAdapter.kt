package com.example.yeebum.screens.adapters.recycler_views

import android.annotation.SuppressLint
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.yeebum.R
import com.example.yeebum.models.Flow
import com.example.yeebum.screens.adapters.recycler_views.view_holders.FlowsViewHolder
import com.example.yeebum.screens.flows_control.FlowsInterface


class FlowsRecyclerViewAdapter(
    private val listener: FlowsInterface,
    private val listOfFlows: List<Flow>
):RecyclerView.Adapter<FlowsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowsViewHolder =
         FlowsViewHolder(
             LayoutInflater.from(parent.context).inflate(
                 R.layout.flow_card,
                 parent,
                 false
             )
         )


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FlowsViewHolder, position: Int) {
        val flow = listOfFlows[holder.adapterPosition]

        //edit or delete flow
        holder.editFlowButton.setOnClickListener {
            val ctw = ContextThemeWrapper(holder.itemView.context, R.style.CustomPopupTheme)//get dark theme
            PopupMenu(ctw, holder.editFlowButton,)
                .run {
                    menuInflater.inflate(R.menu.options_menu, menu)
                    setOnMenuItemClickListener { item->
                        Toast.makeText(holder.itemView.context, item.title, Toast.LENGTH_LONG).show()
                        true
                    }
                    show()
                }
            //listener.onEditFlow(flow)
        }

        //start flow
        holder.allFlowCard.setOnClickListener {
            listener.onStartFlow(flow)
        }


        holder.flowName.text = flow.name

        var duration = 0
        flow.actions.forEach {
            duration+=it.duration/1000
        }
        holder.flowDuration.text =  "${duration}s"
    }

    override fun getItemCount(): Int = listOfFlows.size


}